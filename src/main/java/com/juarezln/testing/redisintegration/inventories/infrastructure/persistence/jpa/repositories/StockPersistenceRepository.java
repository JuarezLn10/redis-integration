package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.repositories;

import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.entities.StockPersistenceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository interface for managing stock information in the inventory management system using JPA.
 */
@Repository
public interface StockPersistenceRepository extends JpaRepository<StockPersistenceEntity, Long> {

    /**
     * Finds the stock information for a given product ID.
     *
     * @param productId the ID of the product for which to find the stock information
     * @return an Optional containing the StockPersistenceEntity if found, or an empty Optional if not found
     */
    Optional<StockPersistenceEntity> findByProduct_Id(Long productId);
}
