package com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Resource class for updating the price of a product in the inventory.
 *
 * @param newPriceAmount the new price amount for the product
 * @param newPriceCurrency the currency of the new price
 */
@Schema(
        name = "UpdateProductPriceResource",
        description = "Resource for updating the price of a product in the inventory."
)
public record UpdateProductPriceResource(
        @Schema(
                description = "The new price amount for the product",
                example = "19.99"
        )
        Double newPriceAmount,

        @Schema(
                description = "The currency of the new price",
                example = "USD"
        )
        String newPriceCurrency
) {
}
