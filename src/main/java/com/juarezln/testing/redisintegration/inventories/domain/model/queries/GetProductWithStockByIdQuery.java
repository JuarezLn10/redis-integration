package com.juarezln.testing.redisintegration.inventories.domain.model.queries;

/**
 * Query object representing a request to retrieve a product along with its stock information by product ID.
 *
 * @param productId the unique identifier of the product to be retrieved, must be a positive number
 */
public record GetProductWithStockByIdQuery(Long productId) {

    public GetProductWithStockByIdQuery {
        if (productId == null || productId <= 0) {
            throw new IllegalArgumentException("Product ID must be a positive number.");
        }
    }
}
