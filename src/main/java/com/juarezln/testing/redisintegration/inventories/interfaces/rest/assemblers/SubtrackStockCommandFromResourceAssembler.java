package com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.commands.SubtrackStockCommand;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.SubtrackStockResource;

/**
 * Assembler class to convert SubtrackStockResource to SubtrackStockCommand.
 */
public class SubtrackStockCommandFromResourceAssembler {

    /**
     * Converts a SubtrackStockResource to a SubtrackStockCommand.
     *
     * @param resource the SubtrackStockResource to convert
     * @return a SubtrackStockCommand with the same productId and quantity as the resource
     */
    public static SubtrackStockCommand toCommandFromResource(Long productId, SubtrackStockResource resource) {
        return new SubtrackStockCommand(
            productId,
            resource.quantity()
        );
    }
}
