package com.juarezln.testing.redisintegration.inventories.infrastructure.persistence.jpa.entities;

import com.juarezln.testing.redisintegration.shared.domain.model.valueobjects.Money;
import com.juarezln.testing.redisintegration.shared.infrastructure.persistence.jpa.entities.AuditableAbstractPersistenceEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Represents a product entity in the inventory system. This class is mapped to the "products" table in the database and includes fields for the product's name, description, and price.
 * The price is represented as an embedded Money value object, which includes both the amount and the currency. The class extends AuditableAbstractPersistenceEntity, which provides common auditing fields such as createdAt
 */
@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
public class ProductPersistenceEntity extends AuditableAbstractPersistenceEntity {

    /**
     * The name of the product. It is a required field with a maximum length of 100 characters.
     */
    @Column(name = "name", nullable = false, length = 100)
    private String name;

    /**
     * The description of the product. It is a required field with a maximum length of 500 characters.
     */
    @Column(name = "description", nullable = false, length = 500)
    private String description;

    /**
     * The price of the product. It is a required field and is represented as an embedded Money value object, which includes both the amount and the currency.
     * The amount is stored in a column named "price_amount" and the currency is stored in a column named "price_currency". Both columns are non-nullable.
     */
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "price_amount", nullable = false)),
            @AttributeOverride(name = "currency", column = @Column(name = "price_currency", nullable = false))
    })
    private Money price;
}
