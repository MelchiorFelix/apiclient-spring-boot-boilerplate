package com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.UpdateRoleRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.UserResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.User;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

class UserDetailsServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private TokenServiceImpl tokenService;

    @Mock
    private ServiceUtilsImpl serviceUtils;

    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoadUserByUsername() {
        String username = "testUsername";
        User mockUser = new User();

        when(userRepository.findByLogin(username)).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        assertNotNull(userDetails);
        assertEquals(mockUser, userDetails);
    }

    @Test
    void testRegisterUserAlreadyExists() {
        AuthenticationRequest request = new AuthenticationRequest("existingUser", "testPassword");
        when(userRepository.findByLogin(request.getLogin())).thenReturn(Optional.of(new User()));

        ResponseEntity responseEntity = userDetailsService.register(request);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testRegisterNewUser() {
        AuthenticationRequest request = new AuthenticationRequest("newUser", "testPassword");
        when(userRepository.findByLogin(request.getLogin())).thenReturn(Optional.empty());

        ResponseEntity responseEntity = userDetailsService.register(request);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testAuthenticateValidUser() {
        AuthenticationRequest request = new AuthenticationRequest("validUser", "testPassword");
        User mockUser = new User(); // Create a mock User object
        when(userRepository.findByLogin(request.getLogin())).thenReturn(Optional.of(mockUser));

        Authentication authentication = mock(Authentication.class);
        when(authenticationManager.authenticate(any())).thenReturn(authentication);

        when(tokenService.generateToken(mockUser)).thenReturn("mockToken");

        ResponseEntity responseEntity = userDetailsService.authenticate(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testAuthenticateInvalidUser() {
        AuthenticationRequest request = new AuthenticationRequest("invalidUser", "testPassword");
        when(userRepository.findByLogin(request.getLogin())).thenReturn(Optional.empty());

        ResponseEntity responseEntity = userDetailsService.authenticate(request);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testAuthenticateAuthenticationException() {
        AuthenticationRequest request = new AuthenticationRequest("exceptionUser", "testPassword");
        User mockUser = new User(); // Create a mock User object
        when(userRepository.findByLogin(request.getLogin())).thenReturn(Optional.of(mockUser));

        when(authenticationManager.authenticate(any())).thenThrow(new AuthenticationException("Mock Exception") {});

        ResponseEntity responseEntity = userDetailsService.authenticate(request);

        assertEquals(HttpStatus.UNAUTHORIZED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
    }

    @Test
    void testUpdateRoleUserExists() {
        UpdateRoleRequest request = new UpdateRoleRequest(UUID.randomUUID(), UserRole.ADMIN);
        User mockUser = new User();
        when(userRepository.findById(request.getId())).thenReturn(Optional.of(mockUser));

        ResponseEntity responseEntity = userDetailsService.updateRole(request);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdateRoleUserNotFound() {
        UpdateRoleRequest request = new UpdateRoleRequest(UUID.randomUUID(), UserRole.ADMIN);
        when(userRepository.findById(request.getId())).thenReturn(Optional.empty());
        when(serviceUtils.notFoundResponse()).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        ResponseEntity responseEntity = userDetailsService.updateRole(request);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testGetAll() {
        List<User> mockUsers = new ArrayList<>(); // Create mock User objects
        mockUsers.add(new User("user1", "pass1", UserRole.USER));
        mockUsers.add(new User("user2", "pass2", UserRole.ADMIN));
        when(userRepository.findAll()).thenReturn(mockUsers);

        ResponseEntity<List<UserResponse>> responseEntity = userDetailsService.getAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());

        List<UserResponse> userResponses = responseEntity.getBody();
        assertEquals(2, userResponses.size());
        assertEquals("user1", userResponses.get(0).getLogin());
        assertEquals("user2", userResponses.get(1).getLogin());
    }
}
