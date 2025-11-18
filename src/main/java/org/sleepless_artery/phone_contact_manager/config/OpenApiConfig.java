package org.sleepless_artery.phone_contact_manager.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;


@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Contacts API",
                version = "1.0",
                description = "API для управления контактами"
        )
)
public class OpenApiConfig {}
