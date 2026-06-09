package com.juarezln.testing.redisintegration.shared.infrastructure.security.spring.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Spring Security configuration class that defines the security filter chain for the application.
 */
@Configuration
public class SpringSecurityConfiguration {

    /**
     * Defines the security filter chain for the application, allowing unauthenticated access to Swagger UI and API documentation endpoints while requiring authentication for all other requests.
     * @param http the HttpSecurity object used to configure the security settings for the application
     * @return the configured SecurityFilterChain object
     * @throws Exception if an error occurs while configuring the security settings
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/swagger-ui.html").permitAll()
                        .anyRequest().authenticated()
                );
        return http.build();
    }
}
