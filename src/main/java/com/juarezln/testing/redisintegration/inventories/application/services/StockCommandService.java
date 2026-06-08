package com.juarezln.testing.redisintegration.inventories.application.services;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.AddStockCommand;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.SubtrackStockCommand;
import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.shared.application.result.ApplicationError;
import com.juarezln.testing.redisintegration.shared.application.result.Result;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Interface for the StockCommandService, which defines the contract for handling stock-related commands.
 */
public interface StockCommandService {

    /**
     * Handles the AddStockCommand, which adds stock to a product.
     *
     * @param command The AddStockCommand containing the details of the stock to be added.
     * @return A Result containing a Pair of the Product and Stock if successful, or an ApplicationError if an error occurs.
     */
    Result<Pair<Product, Stock>, ApplicationError> handle(AddStockCommand command);

    /**
     * Handles the SubtrackStockCommand, which subtracts stock from a product.
     *
     * @param command The SubtrackStockCommand containing the details of the stock to be subtracted.
     * @return A Result containing a Pair of the Product and Stock if successful, or an ApplicationError if an error occurs.
     */
    Result<Pair<Product, Stock>, ApplicationError> handle(SubtrackStockCommand command);
}
