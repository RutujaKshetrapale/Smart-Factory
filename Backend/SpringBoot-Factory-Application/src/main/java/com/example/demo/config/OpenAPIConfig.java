package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenAPIConfig {

    @Bean
    public OpenAPI smartFactoryOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Smart Factory Management System API")
                                .version("1.0.0")
                                .description(
                                        "REST API for managing industrial plants, "
                                        + "machines, sensors, telemetry, alerts, "
                                        + "maintenance, production and energy data."
                                )
                                .contact(
                                        new Contact()
                                                .name("Smart Factory Development Team")
                                )
                );
    }
}