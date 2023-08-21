package com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.config;

import com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Autowired
    private SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf
                        .ignoringRequestMatchers(AntPathRequestMatcher.antMatcher(Constants.H2_CONSOLE + "/**"))
                        .disable()
                )
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST, "/api/clients").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.POST, Constants.AUTH_LOGIN).permitAll()
                        .requestMatchers(Constants.AUTH_WHITELIST).permitAll()
                        .requestMatchers(AntPathRequestMatcher.antMatcher(Constants.H2_CONSOLE + "/**")).permitAll()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(this.securityFilter, UsernamePasswordAuthenticationFilter.class);
        http
               .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin).disable());

        return http.build();
    }
}
