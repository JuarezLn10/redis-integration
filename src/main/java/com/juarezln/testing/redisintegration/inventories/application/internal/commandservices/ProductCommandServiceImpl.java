package com.juarezln.testing.redisintegration.inventories.application.internal.commandservices;

import com.juarezln.testing.redisintegration.inventories.application.services.ProductCommandService;
import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.RegisterProductCommand;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.UpdateProductPriceCommand;
import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.ProductRepository;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.StockRepository;
import com.juarezln.testing.redisintegration.shared.application.result.ApplicationError;
import com.juarezln.testing.redisintegration.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of the ProductCommandService interface, responsible for handling commands related to product management, such as registering new products and updating product prices. This service interacts with the ProductRepository and StockRepository to perform database operations and ensures that all operations are executed within a transactional context to maintain data integrity.
 */
@Slf4j
@Service
public class ProductCommandServiceImpl implements ProductCommandService {

    // Product Repository for handling product-related database operations
    private final ProductRepository productRepository;

    // Stock Repository for handling stock-related database operations
    private final StockRepository stockRepository;

    // Constructor for ProductCommandServiceImpl, which initializes the productRepository and stockRepository fields with the provided instances. This constructor is used for dependency injection, allowing the service to interact with the repositories to perform database operations related to products and stock.
    public ProductCommandServiceImpl(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    /**
     * @inheritDocs
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Long, ApplicationError> handle(RegisterProductCommand command) {
        if (productRepository.existsByName(command.name()))
            return Result.failure(ApplicationError
                    .conflict("Product", "A product with the name '%s' already exists".formatted(command.name())));
        var product = new Product(command);
        try {
            product = productRepository.save(product);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Register Product", "An unexpected error occurred while registering the product: %s".formatted(e.getMessage())));
        }
        log.info("Product with name {} registered successfully", product.getName());
        var stock = new Stock(product, 0);
        try {
            stock = stockRepository.save(stock);
        } catch (Exception e) {
            return Result.failure(ApplicationError.unexpected("Register Stock", "An unexpected error occurred while registering the stock for the product: %s".formatted(e.getMessage())));
        }
        log.info("Stock for product {} registered successfully", product.getName());
        return Result.success(product.getId());
    }

    /**
     * @inheritDocs
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Product, ApplicationError> handle(UpdateProductPriceCommand command) {
        var result = productRepository.findById(command.productId());
        if (result.isEmpty())
            return Result.failure(ApplicationError.notFound("Product", command.productId().toString()));
        var productToUpdate = result.get();
        try {
            var updatedProduct = productRepository.save(productToUpdate.updatePrice(command.newPrice()));
            log.info("Product with id {} updated successfully", productToUpdate.getId());
            return Result.success(updatedProduct);
        } catch (Exception e) {
            log.error("An unexpected error occurred while updating the product with id {}: {}", productToUpdate.getId(), e.getMessage());
            return Result.failure(ApplicationError.unexpected("update-product", e.getMessage()));
        }
    }
}
