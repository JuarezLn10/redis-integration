package com.juarezln.testing.redisintegration.inventories.domain.repositories;

import com.juarezln.testing.redisintegration.inventories.domain.model.aggregates.Product;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for managing Product entities in the inventory management system.
 */
public interface ProductRepository {

    /**
     * Saves a product to the repository. This method can be used for both creating new products and updating existing ones.
     *
     * @param product the product entity to be saved or updated in the repository
     * @return the saved product entity, which may include any generated fields (e.g., ID) after being persisted
     */
    Product save(Product product);

    /**
     * Finds a product by its unique identifier (ID). This method returns an Optional containing the product if found, or an empty Optional if no product with the specified ID exists in the repository.
     *
     * @param id the unique identifier of the product to be retrieved from the repository
     * @return an Optional containing the product if found, or an empty Optional if no product with the specified ID exists in the repository
     */
    Optional<Product> findById(Long id);

    /**
     * Retrieves a list of all products available in the repository. This method returns a list of Product entities, which may be empty if no products are currently stored in the repository.
     *
     * @return a list of all products available in the repository, which may be empty if no products are currently stored
     */
    List<Product> findAll();

    /**
     * Checks if a product with the specified name already exists in the repository. This method returns true if a product with the given name exists, and false otherwise. This can be useful for validating product names before creating or updating products to ensure uniqueness.
     *
     * @param name the name of the product to check for existence in the repository
     * @return true if a product with the specified name exists in the repository, false otherwise
     */
    boolean existsByName(String name);
}
