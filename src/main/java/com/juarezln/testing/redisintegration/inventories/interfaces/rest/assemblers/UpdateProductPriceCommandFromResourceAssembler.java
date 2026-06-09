package com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.commands.UpdateProductPriceCommand;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.UpdateProductPriceResource;
import com.juarezln.testing.redisintegration.shared.interfaces.rest.transform.SharedValueObjectsAssembler;

/**
 * Assembler class responsible for transforming the incoming UpdateProductPriceResource into an UpdateProductPriceCommand that can be processed by the application.
 */
public class UpdateProductPriceCommandFromResourceAssembler {

    /**
     * Transforms the incoming resource into a command that can be processed by the application.
     *
     * @param resource The incoming resource containing the new price information for the product.
     * @return An instance of UpdateProductPriceCommand that can be used to update the product's price in the application.
     */
    public static UpdateProductPriceCommand toCommandFromResource(Long productId, UpdateProductPriceResource resource) {
        return new UpdateProductPriceCommand(
                productId,
                SharedValueObjectsAssembler.toMoneyFromValues(resource.newPriceAmount(), resource.newPriceCurrency())
        );
    }
}
