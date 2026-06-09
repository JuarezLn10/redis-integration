package com.juarezln.testing.redisintegration.shared.infrastructure.persistence.redis.converters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.juarezln.testing.redisintegration.shared.domain.model.valueobjects.Money;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.convert.ReadingConverter;

/**
 * Converter class for converting byte arrays to Money objects when reading from Redis. It implements the Converter interface and is annotated with @ReadingConverter to indicate that it is used for reading data from Redis. The convert method takes a byte array as input and returns a Money object by deserializing the byte array using the ObjectMapper.
 */
@ReadingConverter
public class MoneyReadConverter implements Converter<byte[], Money> {

    // ObjectMapper instance for converting JSON byte arrays to Money objects
    private final ObjectMapper mapper = new ObjectMapper();

    /**
     * Converts a byte array to a Money object by deserializing the byte array using the ObjectMapper. If the conversion fails, it throws a RuntimeException with an appropriate error message.
     *
     * @param source the byte array to be converted to a Money object
     * @return a Money object representation of the byte array
     */
    @Override
    public Money convert(byte[] source) {
        try {
            return mapper.readValue(source, Money.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to convert byte[] to Money", e);
        }
    }
}
