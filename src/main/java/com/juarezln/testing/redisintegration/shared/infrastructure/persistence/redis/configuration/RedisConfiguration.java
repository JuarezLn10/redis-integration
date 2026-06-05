package com.juarezln.testing.redisintegration.shared.infrastructure.persistence.redis.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJacksonJsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * Configuration class for RedisTemplate bean. It sets up the connection factory and serializers for keys and values.
 * The key serializer is set to StringRedisSerializer, while the value serializer is set to GenericJacksonJsonRedisSerializer with unsafe default typing enabled.
 *
 * @version 1.0.0
 */
@Configuration
public class RedisConfiguration {

    /**
     * Creates and configures a RedisTemplate bean with the specified connection factory and serializers.
     *
     * @param connectionFactory the RedisConnectionFactory to be used by the RedisTemplate
     * @return a configured RedisTemplate instance
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory connectionFactory) {

        // Create a new RedisTemplate instance
        RedisTemplate<String, Object> template = new RedisTemplate<>();

        // Set the connection factory for the RedisTemplate
        template.setConnectionFactory(connectionFactory);

        // Create a StringRedisSerializer for serializing keys and hash keys
        StringRedisSerializer stringSerializer = new StringRedisSerializer();

        // Create a GenericJacksonJsonRedisSerializer for serializing values and hash values, enabling unsafe default typing
        GenericJacksonJsonRedisSerializer jsonSerializer =
                GenericJacksonJsonRedisSerializer.builder()
                        .enableUnsafeDefaultTyping()
                        .build();

        // Set the key and hash key serializers to StringRedisSerializer
        template.setKeySerializer(stringSerializer);
        template.setHashKeySerializer(stringSerializer);

        // Set the value and hash value serializers to GenericJacksonJsonRedisSerializer
        template.setValueSerializer(jsonSerializer);
        template.setHashValueSerializer(jsonSerializer);

        // Initialize the RedisTemplate after setting all properties
        template.afterPropertiesSet();

        // Return the configured RedisTemplate instance
        return template;
    }
}
