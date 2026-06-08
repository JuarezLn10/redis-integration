package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.entities;

import com.juarezln.testing.redisintegration.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The StockPersistenceEntity class represents the stock information for a product in the inventory system. It is a JPA entity that maps to the "stocks" table in the database. This class extends AuditableAbstractPersistenceEntity, which provides common auditing fields such as createdAt and updatedAt. The StockPersistenceEntity includes a many-to-one relationship with the ProductPersistenceEntity, indicating that each stock entry is associated with a specific product.
 * It also contains a quantity field to track the amount of stock available for that product.
 */
@Entity
@Table(name = "stocks")
@Getter
@Setter
@NoArgsConstructor
public class StockPersistenceEntity extends AuditableAbstractPersistenceEntity {

    /**
     * The product associated with this stock entry. This field is required and should be set when creating a new stock instance. It is marked with @ManyToOne to indicate a many-to-one relationship with the ProductPersistenceEntity, and it uses lazy fetching to optimize performance by loading the product data only when it is accessed. The @JoinColumn annotation specifies the foreign key column name in the stocks table that references the products table, and it is marked as non-nullable to ensure that every stock entry must be associated with a product.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private ProductPersistenceEntity product;

    /**
     * The quantity of the product available in stock. This field is required and should be set when creating a new stock instance. It is marked with @Column to specify the column name in the database, and it is defined as non-nullable with a column definition of "INT" to ensure that it is stored as an integer value in the database.
     */
    @Column(name = "quantity", nullable = false, columnDefinition = "INT")
    private Integer quantity;
}
