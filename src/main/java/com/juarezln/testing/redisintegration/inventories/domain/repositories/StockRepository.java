package com.juarezln.testing.redisintegration.inventories.domain.repositories;

import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;

import java.util.Optional;

/**
 * Repository interface for managing stock information in the inventory management system.
 */
public interface StockRepository {

    /**
     * Saves the given stock information to the repository.
     *
     * @param stock the Stock entity containing the stock information to be saved
     * @return the saved Stock entity, which may include any updates made during the save operation (e.g., generated ID)
     */
    Stock save(Stock stock);

    /**
     * Finds the stock information for a given product ID.
     *
     * @param productId the ID of the product for which to find the stock information
     * @return an Optional containing the Stock information if found, or an empty Optional if not found
     */
    Optional<Stock> findByProductId(Long productId);
}
