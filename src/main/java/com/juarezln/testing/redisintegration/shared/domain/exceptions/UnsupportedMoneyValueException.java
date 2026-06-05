package com.juarezln.testing.redisintegration.shared.domain.exceptions;

/**
 * Custom exception thrown when an unsupported money value is encountered in the application.
 * This exception is used to indicate that a provided money value does not meet the expected criteria,
 * such as having a negative amount or an unsupported currency.
 */
public class UnsupportedMoneyValueException extends RuntimeException {
    public UnsupportedMoneyValueException(String message) {
        super(message);
    }
}
