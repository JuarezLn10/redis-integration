package com.juarezln.testing.redisintegration.shared.infrastructure.persistence.mongodb.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.util.List;

/**
 * MongoDB configuration class for setting up custom conversions.
 * This class can be extended to include custom converters for MongoDB operations.
 *
 * @version 1.0.0
 */
@Configuration
public class MongoConfiguration {

    /**
     * Defines custom conversions for MongoDB. Currently, it returns an empty list of conversions,
     * but it can be extended to include specific converters as needed.
     *
     * @return a MongoCustomConversions object containing the custom converters for MongoDB operations.
     */
    @Bean
    public MongoCustomConversions mongoCustomConversions() {
        return new MongoCustomConversions(List.of());
    }
}
