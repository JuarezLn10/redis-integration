package com.juarezln.testing.redisintegration.inventories.application.services;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.RegisterProductCommand;
import com.juarezln.testing.redisintegration.inventories.domain.model.commands.UpdateProductPriceCommand;
import com.juarezln.testing.redisintegration.shared.application.result.ApplicationError;
import com.juarezln.testing.redisintegration.shared.application.result.Result;

/**
 * Service interface for handling commands related to product management in the inventory system.
 * This interface defines methods for processing commands such as registering a new product and updating the price of an existing product.
 */
public interface ProductCommandService {

    /**
     * Handles the RegisterProductCommand to register a new product in the inventory.
     *
     * @param command the command containing the details of the product to be registered
     * @return an Optional containing the registered Product if the registration was successful, or an empty Optional if the registration failed
     */
    Result<Long, ApplicationError> handle(RegisterProductCommand command);

    /**
     * Handles the UpdateProductPriceCommand to update the price of an existing product.
     *
     * @param command the command containing the product ID and the new price to be updated
     * @return an Optional containing the updated Product if the update was successful, or an empty Optional if the product was not found or the update failed
     */
    Result<Product, ApplicationError> handle(UpdateProductPriceCommand command);
}
