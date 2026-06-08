package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.repositories;

import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.entities.ProductPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing ProductPersistenceEntity entities in the inventory management system.
 * This interface extends JpaRepository, providing CRUD operations and additional query methods for ProductPersistenceEntity objects. It includes a custom method to check for the existence of a product by its name, which can be useful for ensuring uniqueness of product names in the repository.
 */
@Repository
public interface ProductPersistenceRepository extends JpaRepository<ProductPersistenceEntity, Long> {

    /**
     * Checks if a product with the specified name already exists in the repository. This method returns true if a product with the given name exists, and false otherwise. This can be useful for validating product names before creating or updating products to ensure uniqueness.
     *
     * @param name the name of the product to check for existence in the repository
     * @return true if a product with the specified name exists in the repository, false otherwise
     */
    boolean existsByName(String name);

    /**
     * Checks if a product with the specified name exists in the repository, excluding a product with a specific ID. This method is useful for validating product names during updates to ensure that the new name does not conflict with existing products, while allowing the current product to retain its name if it is not being changed.
     *
     * @param name the name of the product to check for existence in the repository
     * @param id the unique identifier of the product to be excluded from the existence check (typically the ID of the product being updated)
     * @return true if a product with the specified name exists in the repository and has a different ID than the one provided, false otherwise
     */
    boolean existsByNameAndIdIsNot(String name, Long id);
}