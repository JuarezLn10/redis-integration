package com.juarezln.testing.redisintegration.inventories.application.internal.queryservices;

import com.juarezln.testing.redisintegration.inventories.application.services.ProductQueryService;
import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetAllProductsQuery;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetProductByIdQuery;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProductQueryService} interface that handles queries related to products.
 */
@Slf4j
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    // Repository for accessing product data
    private final ProductRepository productRepository;

    // Constructor for dependency injection
    public ProductQueryServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    /**
     * @inheritDocs
     */
    @Override
    public List<Product> handle(GetAllProductsQuery query) {
        var productResults = productRepository.findAll();
        log.info("Found {} products", productResults.size());
        return productResults;
    }

    /**
     * @inheritDocs
     */
    @Override
    public Optional<Product> handle(GetProductByIdQuery query) {
        return productRepository.findById(query.productId());
    }
}
