package com.juarezln.testing.redisintegration.inventories.domain.exceptions;

/**
 * Custom exception thrown when adding stock to a product fails in the inventory management system.
 * This exception can be used to indicate various failure scenarios, such as database errors,
 * validation issues, or other unexpected conditions that prevent successful stock addition.
 */
public class StockTransactionFailedException extends RuntimeException {
    public StockTransactionFailedException(String message) {
        super(message);
    }
}
