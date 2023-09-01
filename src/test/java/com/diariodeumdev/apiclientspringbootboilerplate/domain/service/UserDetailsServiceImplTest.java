package com.diariodeumdev.apiclientspringbootboilerplate.domain.service;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.ErrorResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.TokenResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.User;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.UserRepository;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.TokenServiceImpl;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class UserDetailsServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenServiceImpl tokenService;

    @InjectMocks
    private UserDetailsServiceImpl userService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername() {
        // Arrange
        User user = new User("testuser", "password", UserRole.USER);
        when(userRepository.findByLogin("testuser")).thenReturn(Optional.of(user));

        // Act
        UserDetails userDetails = userService.loadUserByUsername("testuser");

        // Assert
        assertNotNull(userDetails);
        assertEquals("testuser", userDetails.getUsername());
        assertEquals("password", userDetails.getPassword());
    }


    @Test
    public void testRegisterUser() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("newuser", "password");
        when(userRepository.findByLogin("newuser")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userService.register(request);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testRegisterUserAlreadyExists() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("existinguser", "password");
        when(userRepository.findByLogin("existinguser")).thenReturn(Optional.of(new User()));

        // Act
        ResponseEntity<?> response = userService.register(request);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    public void testAuthenticateValidUser() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("existinguser", "password");
        User user = new User("existinguser", new BCryptPasswordEncoder().encode("password"), UserRole.USER);
        when(userRepository.findByLogin("existinguser")).thenReturn(Optional.of(user));
        when(tokenService.generateToken(user)).thenReturn("token");
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // Act
        ResponseEntity<?> response = userService.authenticate(request);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() instanceof TokenResponse);
    }

    @Test
    public void testAuthenticateUserNotFound() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("nonexistent", "password");
        when(userRepository.findByLogin("nonexistent")).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = userService.authenticate(request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }

    @Test
    public void testAuthenticateInvalidPassword() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("existinguser", "wrongpassword");
        User user = new User("existinguser", new BCryptPasswordEncoder().encode("password"), UserRole.USER);
        when(userRepository.findByLogin("existinguser")).thenReturn(Optional.of(user));
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new AuthenticationException("Invalid password") {});

        // Act
        ResponseEntity<?> response = userService.authenticate(request);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof ErrorResponse);
    }
}
