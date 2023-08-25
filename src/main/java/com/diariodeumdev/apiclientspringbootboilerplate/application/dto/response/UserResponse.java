package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response;

import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String login;
    private UserRole role;
}
