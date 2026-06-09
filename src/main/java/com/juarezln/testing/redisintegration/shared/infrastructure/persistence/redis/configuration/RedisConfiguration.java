package com.juarezln.testing.redisintegration.shared.infrastructure.persistence.redis.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.juarezln.testing.redisintegration.shared.infrastructure.persistence.redis.converters.MoneyReadConverter;
import com.juarezln.testing.redisintegration.shared.infrastructure.persistence.redis.converters.MoneyWriteConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.convert.RedisCustomConversions;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * Configuration class for setting up Redis connection and serialization in the inventory management system.
 * This class defines beans for the Redis connection factory, object mapper, and custom conversions to handle the serialization and deserialization of objects stored in Redis. It uses the Lettuce library for Redis connection management and configures the connection with specified host and port values from the application properties. Additionally, it sets up custom converters for handling Money value objects during Redis serialization and deserialization.
 *
 * @version 1.1.0
 */
@Configuration
public class RedisConfiguration {

    /**
     * The host of the Redis server, which is injected from the application properties using the @Value annotation. This field is used to configure the connection factory for Redis.
     */
    @Value("${spring.data.redis.host}")
    private String host;

    /**
     * The port of the Redis server, which is injected from the application properties using the @Value annotation. This field is used to configure the connection factory for Redis.
     */
    @Value("${spring.data.redis.port}")
    private Integer port;

    /**
     * The password for the Redis server, which is injected from the application properties using the @Value annotation. This field is used to configure the connection factory for Redis if authentication is required.
     */
    @Value("${spring.data.redis.password}")
    private String password;

    /**
     * Creates a RedisConnectionFactory bean that is used to establish a connection to the Redis server. It configures the connection factory with the host and port specified in the application properties, as well as client configuration options such as command timeout and shutdown timeout.
     *
     * @return a RedisConnectionFactory instance configured with the specified host, port, and client options
     */
    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {

        // Configure the Redis connection with the specified host and port
        RedisStandaloneConfiguration redisConfiguration =
                new RedisStandaloneConfiguration(host, port);

        // Set the password for the Redis connection if authentication is required
        redisConfiguration.setPassword(password);

        // Configure the Lettuce client with command timeout and shutdown timeout options
        LettuceClientConfiguration clientConfiguration =
                LettuceClientConfiguration.builder()
                        .commandTimeout(Duration.ofSeconds(2))
                        .shutdownTimeout(Duration.ofMillis(100))
                        .build();

        // Create and return a LettuceConnectionFactory with the specified Redis configuration and client configuration
        return new LettuceConnectionFactory(
                redisConfiguration,
                clientConfiguration
        );
    }

    /**
     * Sets up the object mapper for Redis serialization and deserialization. It uses the JsonMapper builder to create an ObjectMapper instance that can handle various data types and formats, including those used in the inventory management system. This ObjectMapper is used by the GenericJacksonJsonRedisSerializer to serialize and deserialize objects stored in Redis.
     * @return an ObjectMapper instance configured for Redis serialization and deserialization
     */
    @Bean
    public ObjectMapper redisObjectMapper() {

        // Create and return an ObjectMapper instance using the JsonMapper builder, which can handle various data types and formats for Redis serialization and deserialization
        return JsonMapper.builder()
                .findAndAddModules()
                .build();
    }

    /**
     * Creates a RedisCustomConversions bean that can be used to register custom converters for Redis serialization and deserialization.
     *
     * @return a RedisCustomConversions instance with registered custom converters
     */
    @Bean
    public RedisCustomConversions redisCustomConversions() {

        // Create a list of custom converters for Redis serialization and deserialization, including converters for handling value objects
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(new MoneyWriteConverter());
        converters.add(new MoneyReadConverter());

        // Create and return a RedisCustomConversions instance with the registered custom converters, which will be used by the RedisTemplate for handling serialization and deserialization of objects stored in Redis
        return new RedisCustomConversions(converters);
    }
}
