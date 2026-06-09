package com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.ProductResource;

/**
 * Assembler class responsible for converting Product entities to ProductResource objects.
 */
public class ProductResourceFromEntityAssembler {

    /**
     * Converts a Product entity to a ProductResource.
     *
     * @param entity the Product entity to convert
     * @return a ProductResource representing the given Product entity
     */
    public static ProductResource toResourceFromEntity(Product entity) {
        return new ProductResource(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice().getAmount(),
                entity.getPrice().getCurrency()
        );
    }
}
