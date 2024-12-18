package com.trungtin.employeeservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import io.swagger.v3.oas.annotations.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(
                title = "Employee API Specification - Trung Tín",
                description = "API documentation for Employee Service",
                version = "v1.0",
                contact = @Contact(
                        name = "Trung Tin",
                        email = "trungtin7166293@gmail.com"
                ),
                license = @License(
                        name = "MIT License"
                ),
                termsOfService = "https://www.google.com/"
        ),
        servers = {
                @Server(
                        description = "Local ENV",
                        url = "http://localhost:9002"
                )
        }
)
@Configuration
public class OpenApiConfig {
}
