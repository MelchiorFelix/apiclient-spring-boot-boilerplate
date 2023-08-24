package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class TokenResponse {
    private String token;
}
