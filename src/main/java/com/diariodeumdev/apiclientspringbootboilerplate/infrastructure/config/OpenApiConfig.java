package com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.SECURITY_SCHEME;

@Configuration
@OpenAPIDefinition(info = @Info(title = "API Client Spring Boot Boilerplate", version = "v2.7"))
@SecurityScheme(
        name = SECURITY_SCHEME,
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
public class OpenApiConfig {
}
