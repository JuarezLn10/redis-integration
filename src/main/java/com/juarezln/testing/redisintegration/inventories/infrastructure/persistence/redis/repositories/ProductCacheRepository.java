package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.redis.repositories;

import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.redis.entities.ProductCacheEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing ProductCacheEntity entities in Redis.
 * This interface extends CrudRepository, providing basic CRUD operations for ProductCacheEntity objects. It allows for saving, retrieving, updating, and deleting product cache entities in the Redis data store.
 * The repository is annotated with @Repository to indicate that it is a Spring Data repository component, which enables exception translation and other repository-related features provided by Spring Data.
 */
@Repository
public interface ProductCacheRepository extends CrudRepository<ProductCacheEntity, String> {
}
