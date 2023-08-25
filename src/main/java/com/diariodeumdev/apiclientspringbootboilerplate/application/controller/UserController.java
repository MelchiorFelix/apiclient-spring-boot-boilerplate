package com.diariodeumdev.apiclientspringbootboilerplate.application.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.UpdateRoleRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.UserResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.ROLE_ADMIN;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private UserDetailsServiceImpl userService;

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest request) {
        return userService.authenticate(request);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid AuthenticationRequest request) throws BindException {
        return userService.register(request);
    }

    @PutMapping("update-role")
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity updateRole(@RequestBody @Valid UpdateRoleRequest request) {
        return userService.updateRole(request);
    }

    @GetMapping
    @PreAuthorize(ROLE_ADMIN)
    public ResponseEntity<List<UserResponse>> getAll() {
        return userService.getAll();
    }
}
