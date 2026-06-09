package com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

/**
 * Resource representing a wrapper for products with their stock information.
 *
 * @param total Total number of products
 * @param products List of products with their stock information
 */
@Schema(
        name = "ProductWrapperResource",
        description = "Resource representing a wrapper for products with their stock information"
)
public record ProductWrapperResource(
        @Schema(
                description = "Total number of products",
                example = "100"
        )
        Integer total,

        @Schema(
                description = "List of products with their stock information"
        )
        List<ProductWithStockResource> products
) {
}
