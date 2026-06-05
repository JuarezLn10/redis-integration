package com.juarezln.testing.redisintegration.inventories.domain.exceptions;

/**
 * Custom exception thrown when an error occurs while updating a product in the inventory.
 * This exception indicates that the product update operation failed, which could be due to various reasons such as database issues, validation errors, or other unexpected conditions.
 */
public class ProductUpdateFailedException extends RuntimeException {
    public ProductUpdateFailedException(String message) {
        super(message);
    }
}
