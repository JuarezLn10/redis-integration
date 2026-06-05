package com.juarezln.testing.redisintegration.inventories.domain.model.commands;

import com.juarezln.testing.redisintegration.inventories.domain.exceptions.ProductUpdateFailedException;
import com.juarezln.testing.redisintegration.shared.domain.model.valueobjects.Money;

/**
 * Command object for updating the price of a product in the inventory.
 *
 * @param productId the ID of the product to update
 * @param newPrice the new price to set for the product
 */
public record UpdateProductPriceCommand(Long productId, Money newPrice) {

    public UpdateProductPriceCommand {
        if (productId == null || productId <= 0) {
            throw new ProductUpdateFailedException("Product ID must be a positive number.");
        }
    }
}
