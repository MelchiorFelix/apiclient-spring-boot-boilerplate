package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ClientRequest {

    @NotEmpty
    @Size(min = 3, max = 100)
    private String name;
}
