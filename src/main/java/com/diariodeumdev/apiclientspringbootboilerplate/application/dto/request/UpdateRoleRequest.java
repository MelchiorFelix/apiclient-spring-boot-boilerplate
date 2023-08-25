package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request;

import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleRequest {

    @NotNull
    private UUID id;

    @NotNull
    private UserRole role;
}
