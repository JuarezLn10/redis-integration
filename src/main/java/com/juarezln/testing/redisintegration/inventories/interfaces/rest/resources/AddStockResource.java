package com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource representing the request body for adding stock to a product in the inventory management system.
 *
 * @param quantity the amount of stock to be added, which must be a positive integer
 */
@Schema(
        name = "AddStockResource",
        description = "Resource representing the request body for adding stock to a product in the inventory management system"
)
public record AddStockResource(
        @Schema(
                description = "The amount of stock to be added, which must be a positive integer",
                example = "10"
        )
        Integer quantity
) {
}
