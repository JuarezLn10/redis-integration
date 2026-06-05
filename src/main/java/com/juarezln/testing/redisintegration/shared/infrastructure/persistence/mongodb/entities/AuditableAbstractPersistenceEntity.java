package com.juarezln.testing.redisintegration.shared.infrastructure.persistence.mongodb.entities;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.FieldType;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.util.Date;

/**
 * Abstract base class for MongoDB entities that includes auditing fields for creation and modification timestamps.
 * This class provides common fields and functionality for all entities that require auditing information.
 */
@Getter
public abstract class AuditableAbstractPersistenceEntity {

    /**
     * The unique identifier for the entity, represented as a MongoDB ObjectId.
     * This field is annotated with @MongoId to indicate that it is the primary key for the MongoDB document, and the FieldType.OBJECT_ID specifies that the value should be stored as an ObjectId in the database. The @Setter annotation from Lombok allows for setting the value of this field, although in practice, it is typically managed by the persistence framework and should not be manually set in application code.
     */
    @Setter
    @MongoId(FieldType.OBJECT_ID)
    private String id;

    /**
     * The timestamp indicating when the entity was created.
     * This field is automatically populated with the current date and time when the entity is first saved to the database.
     */
    @CreatedDate
    @Field
    private Date createdAt;

    /**
     * The timestamp indicating when the entity was last modified.
     * This field is automatically updated with the current date and time whenever the entity is updated in the database.
     */
    @LastModifiedDate
    @Field
    private Date updatedAt;
}
