package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request;

import lombok.*;

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
