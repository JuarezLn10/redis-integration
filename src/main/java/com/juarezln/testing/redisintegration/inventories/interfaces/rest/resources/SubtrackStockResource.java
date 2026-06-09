package com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource representing the request body for subtracting stock from a product in the inventory management system.
 *
 * @param quantity the quantity of stock to subtract, which must be a positive integer
 */
@Schema(
        name = "SubtrackStockResource",
        description = "Resource representing the request body for subtracting stock from a product in the inventory management system."
)
public record SubtrackStockResource(
        @Schema(
                description = "The quantity of stock to subtract, which must be a positive integer",
                example = "10"
        )
        Integer quantity
) {
}
