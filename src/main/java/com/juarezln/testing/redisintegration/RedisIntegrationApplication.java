package com.juarezln.testing.redisintegration;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

/**
 * Main application class for the Redis Integration application.
 * This class is responsible for bootstrapping the Spring Boot application and enabling necessary features such as JPA auditing, MongoDB auditing, and caching.
 *
 * @summary The application is designed to demonstrate the integration of Redis with Spring Boot, along with JPA and MongoDB for data persistence.
 */
@SpringBootApplication
@EnableJpaAuditing
@EnableMongoAuditing
@EnableRedisRepositories
public class RedisIntegrationApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedisIntegrationApplication.class, args);
    }

}
