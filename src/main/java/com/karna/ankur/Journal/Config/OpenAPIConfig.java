package com.karna.ankur.Journal.Config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.security.SecurityScheme.In;
import io.swagger.v3.oas.models.security.SecurityScheme.Type;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * This configuration class sets up Swagger UI using OpenAPI 3.
 * It adds API metadata, server details, tags for grouping endpoints,
 * and security setup for JWT-based authorization.
 */
@Configuration // Marks this class as a Spring configuration
public class OpenAPIConfig {

    // Constant used as the key name for the bearer authentication scheme
    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    /**
     * Creates and returns the OpenAPI specification for Swagger UI.
     * This will be available at: http://localhost:8080/swagger-ui/index.html
     */
    @Bean
    public OpenAPI journalAppOpenAPI() {
        return new OpenAPI()
                // General information shown at the top of Swagger UI
                .info(new Info()
                        .title("Journal App") // API title
                        .version("v1.0") // Version shown in Swagger
                        .description("API documentation for Journal Application.")) // Description below title

                // Adds a server entry so Swagger knows which base URL to use
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080") // Base URL
                                .description("Local Dev Server") // Label in Swagger UI
                ))

                // Tells Swagger that the whole API uses JWT bearer authentication
                .addSecurityItem(new SecurityRequirement()
                        .addList(SECURITY_SCHEME_NAME))

                // Defines what the bearerAuth security scheme means (JWT in header)
                .components(new io.swagger.v3.oas.models.Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME) // Matches the one used in addSecurityItem
                                        .type(Type.HTTP) // This is HTTP-based security
                                        .scheme("bearer") // Use Bearer tokens
                                        .bearerFormat("JWT") // Tell Swagger it's JWT tokens
                                        .in(In.HEADER) // Tokens should be sent in the request header
                        )
                )

                // Optional: Group controller endpoints using tags for better organization
                .addTagsItem(new Tag()
                        .name("user-controller")
                        .description("User management endpoints"))
                .addTagsItem(new Tag()
                        .name("journal-entry-controller-v-2")
                        .description("Journal entry operations"))
                .addTagsItem(new Tag()
                        .name("public-controller")
                        .description("Public endpoints like signup/signin"))
                .addTagsItem(new Tag()
                        .name("admin-controller")
                        .description("Admin-only operations"))
                .addTagsItem(new Tag()
                        .name("health-check")
                        .description("App health status"));
    }
}
