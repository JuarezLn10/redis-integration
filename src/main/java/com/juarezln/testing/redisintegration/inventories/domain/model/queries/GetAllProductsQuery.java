package com.juarezln.testing.redisintegration.inventories.domain.model.queries;

/**
 * Query object representing a request to retrieve all products from the inventory management system.
 * This query does not require any parameters and is used to fetch a list of all products available in the inventory. It can be handled by a query handler that interacts with the data source to return the complete list of products.
 */
public record GetAllProductsQuery() {
}
