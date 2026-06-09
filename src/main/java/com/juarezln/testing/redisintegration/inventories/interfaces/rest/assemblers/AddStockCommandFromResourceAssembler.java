package com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.commands.AddStockCommand;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.AddStockResource;

/**
 * Assembler to convert AddStockResource to AddStockCommand.
 */
public class AddStockCommandFromResourceAssembler {

    /**
     * Converts AddStockResource to AddStockCommand.
     *
     * @param resource the AddStockResource to convert
     * @return the corresponding AddStockCommand
     */
    public static AddStockCommand toCommandFromResource(Long productId, AddStockResource resource) {
        return new AddStockCommand(
            productId,
            resource.quantity()
        );
    }
}
