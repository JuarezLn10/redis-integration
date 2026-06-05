package com.juarezln.testing.redisintegration.shared.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mongodb.lang.Nullable;

/**
 * Resource class representing an error response in the REST API. This class is used to standardize
 *
 * @param code the error code representing the type of error
 * @param message a human-readable message describing the error
 * @param details optional additional details about the error, which can be null if not provided
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public record ErrorResource(
        String code,
        String message,
        @Nullable String details) {

    /**
     * Constructor for ErrorResource without details.
     *
     * @param code the error code representing the type of error
     * @param message a human-readable message describing the error
     */
    public ErrorResource(String code, String message) {
        this(code, message, null);
    }
}
