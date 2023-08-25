package com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.config;

import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.AUTH_WHITELIST;
import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.UPDATE_USERS;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .authorizeHttpRequests(authorize -> authorize
                    .antMatchers(HttpMethod.POST, Constants.AUTH_LOGIN).permitAll()
                    .antMatchers(AUTH_WHITELIST).permitAll()
                    .antMatchers(HttpMethod.POST, Constants.AUTH_LOGIN).permitAll()
                    .antMatchers(Constants.H2_CONSOLE).permitAll()
                    .antMatchers(HttpMethod.PUT, UPDATE_USERS).hasRole(UserRole.ADMIN.name())
                    .anyRequest().authenticated()
            )
            .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);

        httpSecurity
                .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin).disable());

        return httpSecurity.build();
    }
}
