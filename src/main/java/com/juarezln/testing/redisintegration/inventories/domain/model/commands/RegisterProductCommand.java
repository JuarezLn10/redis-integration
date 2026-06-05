package com.juarezln.testing.redisintegration.inventories.domain.model.commands;

import com.juarezln.testing.redisintegration.inventories.domain.exceptions.ProductRegistrationFailedException;
import com.juarezln.testing.redisintegration.shared.domain.model.valueobjects.Money;

/**
 * This record represents a command to register a new product in the inventory system. It encapsulates the necessary information required to create a new product, including its name, description, and price. The constructor of this record includes validation logic to ensure that the product name is not null or blank and that the price is not null. If any of these validations fail, a ProductRegistrationFailedException is thrown with an appropriate error message.
 *
 * @param name the name of the product to be registered. This field is required and cannot be null or blank. It is validated in the constructor, and if it fails validation, a ProductRegistrationFailedException is thrown with a message indicating that the product name cannot be null or blank.
 * @param description a brief description of the product to be registered. This field is optional and can be null or blank. It does not have validation logic in the constructor, allowing for flexibility in providing a description for the product.
 * @param price the price of the product to be registered. This field is required and cannot be null. It is validated in the constructor, and if it fails validation, a ProductRegistrationFailedException is thrown with a message indicating that the product price cannot be null.
 */
public record RegisterProductCommand(
        String name,
        String description,
        Money price
) {

    public RegisterProductCommand {
        if (name == null || name.isBlank()) {
            throw new ProductRegistrationFailedException("Product name cannot be null or blank");
        }
        if (price == null) {
            throw new ProductRegistrationFailedException("Product price cannot be null");
        }
    }
}
