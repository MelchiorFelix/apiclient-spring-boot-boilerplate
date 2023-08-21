package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Valid
public record ClientRequest(
        @NotBlank(message = "Invalid Name: Empty name")
        @NotNull(message = "Invalid Name: Name is NULL")
        String name
) {
}
