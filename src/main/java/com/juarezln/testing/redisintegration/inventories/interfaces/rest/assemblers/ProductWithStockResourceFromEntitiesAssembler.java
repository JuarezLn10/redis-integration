package com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.ProductWithStockResource;
import org.apache.commons.lang3.tuple.Pair;

/**
 * Assembler class responsible for converting Product and Stock entities into ProductWithStockResource for REST API responses.
 */
public class ProductWithStockResourceFromEntitiesAssembler {

    /**
     * Converts a Pair of Product and Stock entities into a ProductWithStockResource.
     *
     * @param productWithStock A Pair containing the Product and its corresponding Stock entities.
     * @return A ProductWithStockResource that represents the combined information of the Product and Stock for API responses.
     */
    public static ProductWithStockResource toResourceFromEntity(Pair<Product, Stock> productWithStock) {
        var product = productWithStock.getLeft();
        var stock = productWithStock.getRight();

        return new ProductWithStockResource(
                product.getId(),
                product.getName(),
                product.getDescription(),
                product.getPrice().getAmount(),
                product.getPrice().getCurrency(),
                stock.getQuantity()
        );
    }
}
