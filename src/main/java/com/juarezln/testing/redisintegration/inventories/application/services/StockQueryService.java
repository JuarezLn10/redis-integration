package com.juarezln.testing.redisintegration.inventories.application.services;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetAllProductsQuery;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetProductByIdQuery;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Optional;

/**
 * Interface for the StockQueryService, which defines the contract for handling stock-related queries.
 */
public interface StockQueryService {

    /**
     * Handles the GetProductByIdQuery to retrieve a product by its ID along with its stock information.
     *
     * @param query the query containing the product ID to retrieve
     * @return a Pair containing an Optional of the Product and an Optional of the Stock. The first element of the Pair is the Optional of the Product, and the second element is the Optional of the Stock. If the product is not found, both Optionals will be empty. If the product is found but the stock information is not available, the first Optional will contain the Product and the second Optional will be empty.
     */
    Pair<Optional<Product>, Optional<Stock>> handle(GetProductByIdQuery query);

    /**
     * Handles the GetAllProductsQuery to retrieve a list of all products along with their stock information.
     *
     * @param query the query object containing any necessary parameters for retrieving all products and their stock information
     * @return a Pair containing a list of all products and a list of their corresponding stock information. The first element of the Pair is the list of products, and the second element is the list of stock information.
     */
    Pair<List<Product>, List<Stock>> handle(GetAllProductsQuery query);
}
