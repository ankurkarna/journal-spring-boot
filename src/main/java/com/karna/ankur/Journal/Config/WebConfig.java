package com.karna.ankur.Journal.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {
    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                "http://localhost:5173", // Vite dev (localhost)
                                "http://127.0.0.1:5173", // Vite dev (127.0.0.1)
                                "https://crynza-journal-application.onrender.com" // <-- Actual deployed frontend URL
                )
                        .allowedMethods("*")
                        .allowedHeaders("*");
            }
        };
    }
}
