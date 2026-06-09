package com.juarezln.testing.redisintegration.shared.infrastructure.persistence.redis.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juarezln.testing.redisintegration.shared.domain.model.valueobjects.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.WritingConverter;

/**
 * Converter class for converting Money objects to byte arrays for writing to Redis. It implements the Converter interface and is annotated with @WritingConverter to indicate that it is used for writing data to Redis. The convert method takes a Money object as input and returns a byte array representation of the Money object by converting its string representation to bytes using UTF-8 encoding.
 */
@WritingConverter
public class MoneyWriteConverter implements Converter<Money, byte[]> {

    // ObjectMapper instance for converting Money objects to JSON byte arrays
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converts a Money object to a byte array by serializing it to JSON using the ObjectMapper. If the conversion fails, it throws a RuntimeException with an appropriate error message.
     *
     * @param source the Money object to be converted to a byte array
     * @return a byte array representation of the Money object
     */
    @Override
    public byte[] convert(Money source) {
        try {
            return mapper.writeValueAsBytes(source);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert Money to byte[]", e);
        }
    }
}
