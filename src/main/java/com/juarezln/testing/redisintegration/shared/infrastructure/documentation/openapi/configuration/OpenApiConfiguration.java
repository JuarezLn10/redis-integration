package com.juarezln.testing.redisintegration.shared.infrastructure.documentation.openapi.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuration class for setting up OpenAPI documentation for the Restock Platform application.
 * This class defines the OpenAPI bean that configures the API documentation, including metadata,
 * security schemes, and server information.
 *
 * @version 1.0.0
 */
@Configuration
public class OpenApiConfiguration {

    // Inject application name from properties file
    @Value("${spring.application.name}")
    String applicationName;

    // Inject application description from properties file
    @Value("${documentation.application.description}")
    String applicationDescription;

    // Inject application version from properties file
    @Value("${documentation.application.version}")
    String applicationVersion;

    /**
     * Creates and configures the OpenAPI instance for the Restock Platform application.
     *
     * @return the configured OpenAPI instance
     */
    @Bean
    public OpenAPI restockPlatformOpenApi() {

        // Create OpenAPI instance
        var openApi = new OpenAPI();

        // Configure API metadata
        openApi.info(new Info()
                        .title(this.applicationName)
                        .description(this.applicationDescription)
                        .version(this.applicationVersion)
                        .contact(new Contact().name("JuarezLn10"))
                        .license(new License().name("MIT License")
                                .url("https://opensource.org/licenses/MIT")));

        // Configure servers URL
        openApi.servers(List.of(
                new Server().url("http://localhost:8080").description("Local development server")
        ));

        // Return the configured OpenAPI instance
        return openApi;
    }
}
