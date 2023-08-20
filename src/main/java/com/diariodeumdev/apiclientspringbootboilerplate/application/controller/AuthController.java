package com.diariodeumdev.apiclientspringbootboilerplate.application.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.AuthorizationServiceImpl;
import jakarta.validation.Valid;
import org.springframework.boot.context.properties.bind.BindException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthorizationServiceImpl authtService;

    public AuthController(AuthorizationServiceImpl authtService){
        this.authtService = authtService;
    }

    @PostMapping("login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationRequest request){
        return authtService.login(request);
    }

    @PostMapping("register")
    public ResponseEntity register(@RequestBody @Valid AuthenticationRequest request) throws BindException {
        return authtService.register(request);
    }
}
