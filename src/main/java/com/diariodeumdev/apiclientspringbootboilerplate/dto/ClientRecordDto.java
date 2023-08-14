package com.diariodeumdev.apiclientspringbootboilerplate.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;

@Valid
public record ClientRecordDto(
        @NotBlank(message = "Invalid Name: Empty name")
        @NotNull(message = "Invalid Name: Name is NULL")
        String name
) {
}
