package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.redis.entities;

import com.juarezln.testing.redisintegration.shared.domain.model.valueobjects.Money;
import com.juarezln.testing.redisintegration.shared.infrastructure.persistence.redis.entities.AbstractPersistenceEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

/**
 * Entity representing a product in the Redis cache for the inventory management system.
 * This entity is used to store product information such as name, description, and price in the Redis cache with a time-to-live (TTL) of 60 seconds.
 */
@Getter
@Setter
@NoArgsConstructor
@RedisHash(value = "products", timeToLive = 60)
public class ProductCacheEntity extends AbstractPersistenceEntity {

    /**
     * The name of the product, which is a string representing the product's name. This field is used to store the name of the product in the Redis cache.
     */
    @Indexed
    private String name;

    /**
     * The description of the product, which is a string providing details about the product. This field is used to store the description of the product in the Redis cache.
     */
    private String description;

    /**
     * The price of the product, which is represented as a Money value object. This field is used to store the price of the product in the Redis cache.
     */
    private Money price;
}
