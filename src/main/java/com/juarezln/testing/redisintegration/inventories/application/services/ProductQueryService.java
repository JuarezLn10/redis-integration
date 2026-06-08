package com.juarezln.testing.redisintegration.inventories.application.services;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetAllProductsQuery;
import com.juarezln.testing.redisintegration.inventories.domain.model.queries.GetProductByIdQuery;

import java.util.List;

public interface ProductQueryService {

    /**
     * Handles the GetAllProductsQuery to retrieve a list of all products.
     *
     * @param query the query object containing any necessary parameters for retrieving all products
     * @return a list of all products available in the inventory
     */
    List<Product> handle(GetAllProductsQuery query);

    /**
     * Handles the GetProductByIdQuery to retrieve a product by its ID.
     *
     * @param query the query containing the product ID to retrieve
     * @return a list containing the product with the specified ID, or an empty list if not found
     */
    List<Product> handle(GetProductByIdQuery query);
}
