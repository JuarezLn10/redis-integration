package com.juarezln.testing.redisintegration.shared.interfaces.rest.transform;

import com.juarezln.testing.redisintegration.shared.application.result.ApplicationError;
import com.juarezln.testing.redisintegration.shared.interfaces.rest.resources.ErrorResource;
import org.jspecify.annotations.NullMarked;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

/**
 * Utility class for assembling error responses in the REST API. This class provides methods to map
 * application errors to appropriate HTTP status codes and to generate localized error messages based on error codes and details.
 */
@NullMarked
public class ErrorResponseAssembler {

    private static final String MESSAGES_BASENAME = "messages";

    private ErrorResponseAssembler() {
        // Private constructor to prevent instantiation
    }

    /**
     * Maps an ApplicationError to an appropriate HTTP ResponseEntity.
     * Automatically selects the correct HTTP status code based on the error code.
     *
     * @param error the ApplicationError to map
     * @return a ResponseEntity with the appropriate HTTP status and error resource
     */
    public static ResponseEntity<ErrorResource> toErrorResponseFromApplicationError(ApplicationError error) {
        HttpStatusCode status = toStatusFromErrorCode(error.code());
        ErrorResource resource = new ErrorResource(error.code(), error.message(), error.details());
        return new ResponseEntity<>(resource, status);
    }

    /**
     * Determines the appropriate HTTP status code for a given error code.
     *
     * @param errorCode the error code string (e.g., "PROFILE_NOT_FOUND", "VALIDATION_ERROR")
     * @return the corresponding HttpStatus
     */
    public static HttpStatusCode toStatusFromErrorCode(String errorCode) {
        return switch (errorCode) {
            case "VALIDATION_ERROR" -> HttpStatus.BAD_REQUEST;
            case String s when s.endsWith("_NOT_FOUND") -> HttpStatus.NOT_FOUND;
            case "BUSINESS_RULE_VIOLATION" -> HttpStatusCode.valueOf(422);
            case String s when s.endsWith("_CONFLICT") -> HttpStatus.CONFLICT;
            case "UNEXPECTED_ERROR" -> HttpStatus.INTERNAL_SERVER_ERROR;
            default -> HttpStatus.INTERNAL_SERVER_ERROR;
        };
    }
}
