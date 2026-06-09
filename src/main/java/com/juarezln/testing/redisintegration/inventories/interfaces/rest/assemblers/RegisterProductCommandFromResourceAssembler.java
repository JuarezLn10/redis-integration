package com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.commands.RegisterProductCommand;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.RegisterProductResource;
import com.juarezln.testing.redisintegration.shared.interfaces.rest.transform.SharedValueObjectsAssembler;

/**
 * Assembler class responsible for transforming RegisterProductResource instances into RegisterProductCommand instances.
 */
public class RegisterProductCommandFromResourceAssembler {

    /**
     * Transforms a RegisterProductResource into a RegisterProductCommand.
     *
     * @param resource The resource containing the data to create the command.
     * @return A RegisterProductCommand populated with data from the resource.
     */
    public static RegisterProductCommand toCommandFromResource(RegisterProductResource resource) {
        return new RegisterProductCommand(
                resource.name(),
                resource.description(),
                SharedValueObjectsAssembler.toMoneyFromValues(resource.priceAmount(), resource.priceCurrency())
        );
    }
}
