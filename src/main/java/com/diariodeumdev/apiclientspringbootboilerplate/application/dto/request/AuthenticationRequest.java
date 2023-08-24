package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    @NotEmpty
    private String login;
    @NotEmpty
    private String password;
}
