package com.juarezln.testing.redisintegration.shared.domain.model.valueobjects;

import com.juarezln.testing.redisintegration.shared.domain.exceptions.UnsupportedMoneyValueException;

import java.util.List;

/**
 * Value object representing a monetary value, consisting of an amount and a currency. This class provides methods for basic arithmetic operations (addition, subtraction, multiplication) while ensuring that the operations are performed on compatible currencies and that the resulting values are valid.
 *
 * @param amount the monetary amount, which must be a non-negative value
 * @param currency the currency of the monetary value, which must be one of the supported currencies or defaults to USD if not provided
 */
public record Money(
        Double amount,
        String currency
) {

    // Currencies supported by the application
    private static final List<String> SUPPORTED_CURRENCIES = List.of("USD", "EUR", "GBP", "JPY", "PEN");

    // Default currency if not provided
    private static final String DEFAULT_CURRENCY = "USD";

    // Constructor with validation
    public Money {
        if (amount == null || amount < 0) {
            throw new UnsupportedMoneyValueException("Amount must be a non-negative value.");
        }

        if (currency == null || currency.isBlank()) {
            currency = DEFAULT_CURRENCY;
        } else if (!SUPPORTED_CURRENCIES.contains(currency)) {
            throw new UnsupportedMoneyValueException("Unsupported currency: " + currency);
        }
    }

    /**
     * Adds another Money value to this one, ensuring that both have the same currency.
     *
     * @param other the other Money value to add
     * @return a new Money instance representing the sum of the two values
     * @throws UnsupportedMoneyValueException if the currencies are different
     */
    public Money add(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new UnsupportedMoneyValueException("Cannot add money with different currencies.");
        }
        return new Money(this.amount + other.amount, this.currency);
    }

    /**
     * Subtracts another Money value from this one, ensuring that both have the same currency and that the result is not negative.
     *
     * @param other the other Money value to subtract
     * @return a new Money instance representing the difference of the two values
     * @throws UnsupportedMoneyValueException if the currencies are different or if the resulting amount is negative
     */
    public Money subtract(Money other) {
        if (!this.currency.equals(other.currency)) {
            throw new UnsupportedMoneyValueException("Cannot subtract money with different currencies.");
        }
        double resultAmount = this.amount - other.amount;
        if (resultAmount < 0) {
            throw new UnsupportedMoneyValueException("Resulting amount cannot be negative.");
        }
        return new Money(resultAmount, this.currency);
    }

    /**
     * Multiplies the Money value by a non-negative factor, returning a new Money instance with the same currency.
     *
     * @param factor the non-negative factor to multiply the amount by
     * @return a new Money instance representing the result of the multiplication
     * @throws UnsupportedMoneyValueException if the factor is negative
     */
    public Money multiply(double factor) {
        if (factor < 0) {
            throw new UnsupportedMoneyValueException("Factor must be a non-negative value.");
        }
        return new Money(this.amount * factor, this.currency);
    }

    /**
     * Returns the amount of the money value object.
     *
     * @return the amount as a double
     */
    public Double getAmount() {
        return amount;
    }

    /**
     * Returns the currency of the money value object.
     *
     * @return the currency as a string
     */
    public String getCurrency() {
        return currency;
    }
}
