package com.diariodeumdev.apiclientspringbootboilerplate.application.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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

    @PutMapping("update-user")
    public ResponseEntity updateUser() {
        return ResponseEntity.ok("Foi");
    }
}
