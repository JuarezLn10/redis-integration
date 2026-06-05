package com.juarezln.testing.redisintegration.inventories.domain.exceptions;

/**
 * Custom exception class that represents a failure during the product registration process. This exception is thrown when an error occurs while trying to register a new product in the inventory system, such as validation failures, database errors, or other issues that prevent the successful creation of a product. By extending RuntimeException, this exception can be thrown without the need for explicit handling, allowing it to propagate up the call stack and be handled by a global exception handler or other error-handling mechanisms in the application.
 */
public class ProductRegistrationFailedException extends RuntimeException {
    public ProductRegistrationFailedException(String message) {
        super(message);
    }
}
