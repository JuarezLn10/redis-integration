package com.juarezln.testing.redisintegration.shared.interfaces.rest.transform;

import com.juarezln.testing.redisintegration.shared.domain.model.valueobjects.Money;

/**
 * Assembler class responsible for transforming shared value objects from REST resources.
 */
public class SharedValueObjectsAssembler {

    /**
     * Transforms amount and currency values into a Money value object.
     *
     * @param amount the amount value
     * @param currency the currency value
     * @return the Money value object created from the provided amount and currency
     */
    public static Money toMoneyFromValues(Double amount, String currency) {
        return new Money(amount, currency);
    }
}
