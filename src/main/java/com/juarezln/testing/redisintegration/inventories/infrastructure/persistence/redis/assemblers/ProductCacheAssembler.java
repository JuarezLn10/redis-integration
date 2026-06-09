package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.redis.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.redis.entities.ProductCacheEntity;

/**
 * Assembler class responsible for converting between Product domain models and ProductCacheEntity objects used for caching in Redis. This class provides static methods to facilitate the transformation of data between the two representations, ensuring that the fields are correctly mapped and that any necessary type conversions (such as converting product IDs to and from string format) are handled appropriately.
 */
public final class ProductCacheAssembler {

    private ProductCacheAssembler() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a ProductCacheEntity from Redis to a Product domain model. This method maps the fields from the ProductCacheEntity to the corresponding fields in the Product domain model, including converting the product ID from a string format back to a Long type.
     *
     * @param entity the ProductCacheEntity retrieved from Redis to be converted to a Product domain model
     * @return a Product domain model representing the given ProductCacheEntity, or null if the input entity is null
     */
    public static Product toDomainFromCache(ProductCacheEntity entity) {
        if (entity == null) return null;

        var product = new Product();
        product.setId(Long.parseLong(entity.getId()));
        product.setName(entity.getName());
        product.setDescription(entity.getDescription());
        product.setPrice(entity.getPrice());

        return product;
    }

    /**
     * Converts a Product domain model to a ProductCacheEntity for caching in Redis. This method maps the fields from the Product domain model to the corresponding fields in the ProductCacheEntity, including converting the product ID to a string format suitable for Redis storage.
     *
     * @param product the Product domain model to be converted to a ProductCacheEntity for caching in Redis
     * @return a ProductCacheEntity representing the given Product domain model, or null if the input product is null
     */
    public static ProductCacheEntity toCacheFromDomain(Product product) {
        if (product == null) return null;

        var entity = new ProductCacheEntity();
        if (product.getId() != null) {
            entity.setId(product.getId().toString());
        }
        entity.setName(product.getName());
        entity.setDescription(product.getDescription());
        entity.setPrice(product.getPrice());

        return entity;
    }
}
