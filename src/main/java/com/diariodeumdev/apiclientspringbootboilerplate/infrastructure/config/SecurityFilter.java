package com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.config;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.ErrorResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.UserRepository;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.TokenServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.NoSuchElementException;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.TOKEN_USER_NOT_FOUND_MESSAGE;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenServiceImpl tokenService;

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        var token = this.recoverToken(request);
        if (token != null) {
            var login = tokenService.validateToken(token);

            try {
                UserDetails user = userRepository.findByLogin(login)
                        .orElseThrow(() -> new NoSuchElementException());

                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (NoSuchElementException e) {
                ErrorResponse errorResponse = new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        HttpServletResponse.SC_NOT_FOUND,
                        Collections.singletonList(TOKEN_USER_NOT_FOUND_MESSAGE)
                        );
                sendErrorResponse(response, errorResponse, HttpServletResponse.SC_NOT_FOUND);
                return;
            }
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if(authHeader == null) return null;
        return authHeader.replace("Bearer ", "");
    }

    private void sendErrorResponse(HttpServletResponse response, ErrorResponse errorResponse, int statusCode) throws IOException {
        response.setStatus(statusCode);
        response.setContentType("application/json");

        try (PrintWriter writer = response.getWriter()) {
            String jsonResponse = convertErrorResponseToJson(errorResponse);
            writer.write(jsonResponse);
        }
    }

    private String convertErrorResponseToJson(ErrorResponse errorResponse) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(errorResponse);
        } catch (Exception e) {
            return "";
        }
    }
}
