package com.juarezln.testing.redisintegration.inventories.infrastructure.adapters;

import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.domain.repositories.StockRepository;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.assemblers.StockPersistenceAssembler;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.repositories.StockPersistenceRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Implementation of the StockRepository interface using JPA for database operations.
 * This class serves as an adapter between the domain layer and the persistence layer.
 */
@Repository
public class StockRepositoryImpl implements StockRepository {

    // JPA repository for database operations
    private final StockPersistenceRepository stockJpaRepository;

    // Constructor injection for the JPA repository
    public StockRepositoryImpl(StockPersistenceRepository stockJpaRepository) {
        this.stockJpaRepository = stockJpaRepository;
    }

    /**
     * @inheritDocs
     */
    @Override
    public Stock save(Stock stock) {
        var saved = stockJpaRepository.save(StockPersistenceAssembler.toPersistenceFromDomain(stock));
        return StockPersistenceAssembler.toDomainFromPersistence(saved);
    }

    /**
     * @inheritDocs
     */
    @Override
    public Optional<Stock> findByProductId(Long productId) {
        return stockJpaRepository
                .findByProduct_Id(productId)
                .map(StockPersistenceAssembler::toDomainFromPersistence);
    }
}
