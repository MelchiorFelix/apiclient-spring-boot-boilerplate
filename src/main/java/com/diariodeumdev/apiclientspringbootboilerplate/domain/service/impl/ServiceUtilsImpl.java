package com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl;

import lombok.Generated;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Generated
public class ServiceUtilsImpl {

    public ResponseEntity notFoundResponse() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
