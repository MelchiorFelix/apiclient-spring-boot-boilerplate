package com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.config;

public final class Constants {
    public static final String[] AUTH_WHITELIST = {
            // -- Swagger UI v2
            "/v2/api-docs",
            "v2/api-docs",
            "/swagger-resources",
            "swagger-resources",
            "/swagger-resources/**",
            "swagger-resources/**",
            "/configuration/ui",
            "configuration/ui",
            "/configuration/security",
            "configuration/security",
            "/swagger-ui.html",
            "swagger-ui.html",
            "webjars/**",
            // -- Swagger UI v3
            "/v3/api-docs/**",
            "v3/api-docs/**",
            "/swagger-ui/**",
            "swagger-ui/**",
            // CSA Controllers
            "/csa/api/token",
            // Actuators
            "/actuator/**",
            "/health/**",
            "/swagger"
    };

    public static final String H2_CONSOLE = "/h2-console";

    public static final String[] AUTH_LOGIN = {
            // Auth
            "/api/auth/register",
           "/api/auth/login"
    };
}
