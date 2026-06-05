package com.juarezln.testing.redisintegration.inventories.domain.model.queries;

/**
 * Query object representing a request to retrieve a product by its unique identifier.
 *
 * @param productId the unique identifier of the product to be retrieved, must be a positive number
 */
public record GetProductByIdQuery(Long productId) {

    public GetProductByIdQuery {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number.");
        }
    }
}
