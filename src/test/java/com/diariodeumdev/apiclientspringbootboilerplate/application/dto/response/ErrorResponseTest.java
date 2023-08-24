package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response;

import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ErrorResponseTest {

    @Test
    void givenTimestampStatusAndErrors_whenCreatingErrorResponse_thenPropertiesSetCorrectly() {
        // Given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int status = 400;
        List<String> errors = Arrays.asList("Error 1", "Error 2");

        // When
        ErrorResponse errorResponse = new ErrorResponse(timestamp, status, errors);

        // Then
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(errors, errorResponse.getErrors());
    }

    @Test
    void givenEmptyErrorResponse_whenSettingProperties_thenPropertiesUpdated() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse();

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int status = 500;
        List<String> errors = Arrays.asList("Error 3", "Error 4");

        // When
        errorResponse.setTimestamp(timestamp);
        errorResponse.setStatus(status);
        errorResponse.setErrors(errors);

        // Then
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(errors, errorResponse.getErrors());
    }

    @Test
    void givenEmptyErrorResponse_whenUsingNoArgsConstructor_thenDefaultProperties() {
        // Given
        ErrorResponse errorResponse = new ErrorResponse();

        // Then
        assertEquals(0, errorResponse.getStatus());
        assertEquals(null, errorResponse.getTimestamp());
        assertEquals(null, errorResponse.getErrors());
    }

    @Test
    void givenTimestampStatusAndErrors_whenUsingAllArgsConstructor_thenPropertiesSetCorrectly() {
        // Given
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        int status = 404;
        List<String> errors = Arrays.asList("Error 5");

        // When
        ErrorResponse errorResponse = new ErrorResponse(timestamp, status, errors);

        // Then
        assertEquals(timestamp, errorResponse.getTimestamp());
        assertEquals(status, errorResponse.getStatus());
        assertEquals(errors, errorResponse.getErrors());
    }
}
