package com.juarezln.testing.redisintegration.inventories.domain.model.commands;

import com.juarezln.testing.redisintegration.inventories.domain.exceptions.StockTransactionFailedException;

/**
 * Command object representing the action of adding stock to a product in the inventory management system.
 *
 * @param productId the unique identifier of the product to which stock will be added
 * @param quantity the amount of stock to be added, which must be a positive integer
 */
public record AddStockCommand(Long productId, Integer quantity) {

    public AddStockCommand {
        if (productId == null) {
            throw new StockTransactionFailedException("Product ID cannot be null");
        }
        if (quantity == null || quantity <= 0) {
            throw new StockTransactionFailedException("Quantity must be a positive integer");
        }
    }
}
