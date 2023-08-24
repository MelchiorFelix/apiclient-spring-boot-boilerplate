package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private Timestamp timestamp;
    private int status;
    private List<String> errors;
}
