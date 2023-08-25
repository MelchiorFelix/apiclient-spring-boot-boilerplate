package com.diariodeumdev.apiclientspringbootboilerplate.application.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.UpdateRoleRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.UserResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.UserDetailsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UserControllerTest {

    @Mock
    private UserDetailsServiceImpl userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLogin() {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        ResponseEntity expectedResponse = ResponseEntity.ok().build();
        when(userService.authenticate(authenticationRequest)).thenReturn(expectedResponse);

        ResponseEntity response = userController.login(authenticationRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    void testRegister() throws BindException {
        AuthenticationRequest authenticationRequest = new AuthenticationRequest("username", "password");
        ResponseEntity expectedResponse = ResponseEntity.ok().build();
        when(userService.register(authenticationRequest)).thenReturn(expectedResponse);

        ResponseEntity response = userController.register(authenticationRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    void testUpdateRole() {
        UpdateRoleRequest updateRoleRequest = new UpdateRoleRequest(UUID.randomUUID(), UserRole.ADMIN);
        ResponseEntity expectedResponse = ResponseEntity.ok().build();
        when(userService.updateRole(updateRoleRequest)).thenReturn(expectedResponse);

        ResponseEntity response = userController.updateRole(updateRoleRequest);

        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetAll() {
        List<UserResponse> userResponses = List.of(new UserResponse(UUID.randomUUID(), "username", UserRole.USER));
        ResponseEntity<List<UserResponse>> expectedResponse = ResponseEntity.ok(userResponses);
        when(userService.getAll()).thenReturn(expectedResponse);

        ResponseEntity<List<UserResponse>> response = userController.getAll();

        assertEquals(expectedResponse, response);
    }
}
