package com.juarezln.testing.redisintegration.inventories.domain.model.commands;

import com.juarezln.testing.redisintegration.inventories.domain.exceptions.StockTransactionFailedException;

/**
 * Command object representing the action of subtracting stock from a product in the inventory management system.
 *
 * @param productId the unique identifier of the product from which stock will be subtracted
 * @param quantity the quantity of stock to subtract, which must be a positive integer
 */
public record SubtrackStockCommand(Long productId, Integer quantity) {

    public SubtrackStockCommand {
        if (productId == null) {
            throw new StockTransactionFailedException("Product ID cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new StockTransactionFailedException("Quantity must be a positive integer");
        }
    }
}
