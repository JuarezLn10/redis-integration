package com.juarezln.testing.redisintegration.inventories.application.internal.commandservices;

import com.juarezln.testing.redisintegration.inventories.application.services.StockCommandService;
import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.AddStockCommand;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.SubtrackStockCommand;
import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.ProductRepository;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.StockRepository;
import com.juarezln.testing.redisintegration.shared.application.result.ApplicationError;
import com.juarezln.testing.redisintegration.shared.application.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Supplier;
import java.util.function.UnaryOperator;

/**
 * Implementation of the StockCommandService interface that handles commands related to stock management, such as adding or subtracting stock for a product. This service interacts with the ProductRepository and StockRepository to retrieve and update the relevant data. It uses a functional approach to handle the operations and returns results encapsulated in a Result type, which can represent either a successful outcome or an error.
 */
@Slf4j
@Service
public class StockCommandServiceImpl implements StockCommandService {

    // Repository for managing product data
    private final ProductRepository productRepository;

    // Repository for managing stock data
    private final StockRepository stockRepository;

    // Constructor injection of the required repositories
    public StockCommandServiceImpl(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    /**
     * @inheritDocs
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Pair<Product, Stock>, ApplicationError> handle(AddStockCommand command) {
        return updateStock(
                command.productId(),
                command.quantity(),
                stock -> stock.increaseQuantity(command.quantity()),
                "add-stock");
    }

    /**
     * @inheritDocs
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Pair<Product, Stock>, ApplicationError> handle(SubtrackStockCommand command) {
        return updateStock(
                command.productId(),
                command.quantity(),
                stock -> stock.decreaseQuantity(command.quantity()),
                "subtract-stock");
    }

    /**
     * Retrieves the product for the given product ID. If the product is not found, returns a failure result with an appropriate error message.
     *
     * @param productId the ID of the product being retrieved
     * @return a Result containing the Product if found, or an ApplicationError if not found
     */
    private Result<Product, ApplicationError> getProduct(Long productId) {
        return productRepository.findById(productId)
                .<Result<Product, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(
                        ApplicationError.notFound("Product", productId.toString())));
    }

    /**
     * Retrieves the stock for the given product ID. If the stock is not found, returns a failure result with an appropriate error message.
     *
     * @param productId the ID of the product whose stock is being retrieved
     * @return a Result containing the Stock if found, or an ApplicationError if not found
     */
    private Result<Stock, ApplicationError> getStock(Long productId) {
        return stockRepository.findByProductId(productId)
                .<Result<Stock, ApplicationError>>map(Result::success)
                .orElseGet(() -> Result.failure(
                        ApplicationError.notFound(
                                "Stock",
                                "Stock for product with id '%s' not found"
                                        .formatted(productId))));
    }

    /**
     * Performs the given stock operation (increase or decrease) and saves the updated stock.
     *
     * @param productId the ID of the product whose stock is being updated
     * @param stockOperation a Supplier that performs the stock operation (either increase or decrease) and returns the updated Stock
     * @param action a string describing the action being performed (e.g., "add-stock" or "subtract-stock") for logging purposes
     * @param quantity the quantity being added or subtracted, used for logging purposes
     * @return a Result containing the updated Stock if the operation was successful, or an ApplicationError if an error occurred during saving
     */
    private Result<Stock, ApplicationError> saveStock(
            Long productId,
            Supplier<Stock> stockOperation,
            String action,
            Integer quantity) {

        try {
            var stockUpdated = stockRepository.save(stockOperation.get());
            log.info("Stock for product with id '{}' {} by {}. New quantity: {}",
                    productId,
                    action,
                    quantity,
                    stockUpdated.getQuantity()
            );
            return Result.success(stockUpdated);
        } catch (Exception e) {
            log.error("An error occurred while {} stock for product with id '{}': {}",
                    action,
                    productId,
                    e.getMessage()
            );
            return Result.failure(
                    ApplicationError.unexpected(
                            action,
                            "An unexpected error occurred while %s stock for product with id '%s'"
                                    .formatted(action, productId))
            );
        }
    }

    /**
     * Performs the stock update operation (either addition or subtraction) for the given product ID and quantity, using the provided stock operation function. It first retrieves the product and stock, then applies the stock operation and saves the updated stock. The result contains a pair of the product and the updated stock if successful, or an ApplicationError if any step fails.
     *
     * @param productId the ID of the product whose stock is being updated
     * @param quantity the quantity being added or subtracted, used for logging purposes
     * @param operation a UnaryOperator that takes the current Stock and returns the updated Stock after applying the desired operation (either increase or decrease)
     * @param action a string describing the action being performed (e.g., "add-stock" or "subtract-stock") for logging purposes
     * @return a Result containing a Pair of the Product and the updated Stock if the operation was successful, or an ApplicationError if any step failed (e.g., product not found, stock not found, or error during saving)
     */
    private Result<Pair<Product, Stock>, ApplicationError> updateStock(
            Long productId,
            int quantity,
            UnaryOperator<Stock> operation,
            String action) {

        return getProduct(productId)
                .flatMap(product ->
                        getStock(productId)
                                .flatMap(stock ->
                                        saveStock(
                                                productId,
                                                () -> operation.apply(stock),
                                                action,
                                                quantity
                                        ).map(updatedStock ->
                                                Pair.of(product, updatedStock)
                                        )
                                )
                );
    }
}
