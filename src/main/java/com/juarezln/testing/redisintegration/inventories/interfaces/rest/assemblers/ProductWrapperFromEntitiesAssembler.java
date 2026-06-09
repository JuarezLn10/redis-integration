package com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.ProductWithStockResource;
import com.juarezln.testing.redisintegration.inventories.interfaces.rest.resources.ProductWrapperResource;
import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;

import static com.juarezln.testing.redisintegration.inventories.interfaces.rest.assemblers.ProductWithStockResourceFromEntitiesAssembler.toResourceFromEntity;

/**
 * Assembler class responsible for converting Product and Stock entities into ProductWithStockResource and ProductWrapperResource for REST API responses.
 */
public class ProductWrapperFromEntitiesAssembler {

    /**
     * Converts a list of Product and Stock entities into a ProductWrapperResource containing a list of ProductWithStockResource.
     *
     * @param products the list of Product entities to be converted
     * @param stocks the list of Stock entities corresponding to the products, used to retrieve stock information for each product
     * @return a ProductWrapperResource that contains a list of ProductWithStockResource, each representing a product along with its stock information
     */
    public static ProductWrapperResource toWrapperFromEntities(List<Product> products, List<Stock> stocks) {
        var productsWithStock = new ArrayList<ProductWithStockResource>(List.of());
        for (Product product : products) {
            var stock = stocks.stream()
                    .filter(s -> s.getProduct().getId().equals(product.getId()))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Stock not found for product with id: " + product.getId()));
            var pair = Pair.of(product, stock);
            productsWithStock.add(toResourceFromEntity(pair));
        }

        return new ProductWrapperResource(productsWithStock.size(), productsWithStock);
    }
}
