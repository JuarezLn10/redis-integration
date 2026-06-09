package com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * This record represents a resource for registering a new product in the inventory system. It encapsulates the necessary information required to create a new product, including its name, description, price amount, and price currency. The fields in this record are annotated with Swagger annotations to provide metadata for API documentation purposes.
 *
 * @param name the name of the product to be registered. This field is required and cannot be null or blank. It is annotated with a Swagger @Schema annotation that provides a description and an example value for API documentation.
 * @param description a brief description of the product to be registered. This field is optional and can be null or blank. It is annotated with a Swagger @Schema annotation that provides a description and an example value for API documentation.
 * @param priceAmount the price amount of the product to be registered. This field is required and cannot be null. It is annotated with a Swagger @Schema annotation that provides a description and an example value for API documentation.
 * @param priceCurrency the currency of the product price. This field is required and cannot be null. It is annotated with a Swagger @Schema annotation that provides a description and an example value for API documentation.
 */
@Schema(
        name = "RegisterProductResource",
        description = "Resource representing the data required to register a new product in the inventory system."
)
public record RegisterProductResource(
        @Schema(
                description = "The name of the product to be registered. This field is required and cannot be null or blank.",
                example = "Wireless Mouse"
        )
        String name,

        @Schema(
                description = "A brief description of the product to be registered. This field is optional and can be null or blank.",
                example = "A high-precision wireless mouse with ergonomic design."
        )
        String description,

        @Schema(
                description = "The price of the product to be registered. This field is required and cannot be null.",
                example = "29.99"
        )
        Double priceAmount,

        @Schema(
                description = "The currency of the product price. This field is required and cannot be null.",
                example = "USD"
        )
        String priceCurrency
) {
}
