package com.juarezln.testing.redisintegration.inventories.application.internal.queryservices;

import com.juarezln.testing.redisintegration.inventories.application.services.ProductQueryService;
import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetAllProductsQuery;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetProductByIdQuery;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.ProductRepository;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.StockRepository;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementation of the {@link ProductQueryService} interface that handles queries related to products and their stock.
 */
@Slf4j
@Service
public class ProductQueryServiceImpl implements ProductQueryService {

    // Repository for accessing product data
    private final ProductRepository productRepository;

    // Repository for accessing stock data
    private final StockRepository stockRepository;

    // Constructor for dependency injection
    public ProductQueryServiceImpl(ProductRepository productRepository, StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    /**
     * @inheritDocs
     */
    @Override
    public Pair<List<Product>, List<Stock>> handle(GetAllProductsQuery query) {
        var productResults = productRepository.findAll();
        var stockResults = new ArrayList<Stock>(List.of());

        for (var productResult : productResults) {
            var stockResult = stockRepository.findByProductId(productResult.getId());
            if (stockResult.isPresent()) {
                stockResults.add(stockResult.get());
            } else {
                log.warn("Stock not found for product with id: {}", productResult.getId());
            }
        }
        log.info("Found {} products", productResults.size());

        return Pair.of(productResults, stockResults);
    }

    /**
     * @inheritDocs
     */
    @Override
    public Pair<Optional<Product>, Optional<Stock>> handle(GetProductByIdQuery query) {
        var productResult = productRepository.findById(query.productId());
        var stockResult = stockRepository.findByProductId(query.productId());

        return Pair.of(productResult, stockResult);
    }
}
