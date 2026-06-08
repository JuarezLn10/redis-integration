package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.assemblers;

import com.juarezln.testing.redisintegration.inventories.domain.model.entities.Stock;
import com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.entities.StockPersistenceEntity;

/**
 * The StockPersistenceAssembler class is responsible for converting between the Stock domain entity and the StockPersistenceEntity used for database persistence. It provides static methods to transform a StockPersistenceEntity into a Stock domain entity and vice versa. This assembler ensures that the data is correctly mapped between the two representations, allowing for seamless integration between the domain model and the persistence layer. The methods handle null checks to prevent NullPointerExceptions and utilize the ProductPersistenceAssembler to convert associated Product entities as needed.
 */
public final class StockPersistenceAssembler {

    private StockPersistenceAssembler() {
        // Private constructor to prevent instantiation
    }

    /**
     * Converts a StockPersistenceEntity to a Stock domain entity. This method takes a StockPersistenceEntity as input and creates a new Stock domain entity, copying the relevant fields from the persistence entity to the domain entity. If the input entity is null, the method returns null. The associated Product is also converted using the ProductPersistenceAssembler to ensure that all related data is properly transformed for use in the domain model.
     *
     * @param entity the StockPersistenceEntity to be converted to a domain entity
     * @return a Stock domain entity representing the same stock information as the input StockPersistenceEntity, or null if the input entity is null
     */
    public static Stock toDomainFromPersistence(StockPersistenceEntity entity) {
        if (entity == null) return null;

        var stock = new Stock();
        stock.setId(entity.getId());
        stock.setProduct(ProductPersistenceAssembler.toDomainFromPersistence(entity.getProduct()));
        stock.setQuantity(entity.getQuantity());

        return stock;
    }

    /**
     * Converts a Stock domain entity to a StockPersistenceEntity for database persistence. This method takes a Stock object as input and creates a new StockPersistenceEntity, copying the relevant fields from the domain entity to the persistence entity. If the Stock object has an ID, it is set in the persistence entity; otherwise, it is left null for the database to generate. The associated Product is also converted using the ProductPersistenceAssembler to ensure that all related data is properly transformed for persistence.
     *
     * @param stock the Stock domain entity to be converted to a persistence entity
     * @return a StockPersistenceEntity representing the same stock information as the input Stock domain entity, ready for database persistence
     */
    public static StockPersistenceEntity toPersistenceFromDomain(Stock stock) {
        if (stock == null) return null;

        var entity = new StockPersistenceEntity();
        if (stock.getId() != null) {
            entity.setId(stock.getId());
        }
        entity.setProduct(ProductPersistenceAssembler.toPersistenceFromDomain(stock.getProduct()));
        entity.setQuantity(stock.getQuantity());

        return entity;
    }
}
