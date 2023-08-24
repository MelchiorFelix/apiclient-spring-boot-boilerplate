package com.diariodeumdev.apiclientspringbootboilerplate.application.config;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.ErrorResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @InjectMocks
    private GlobalExceptionHandler globalExceptionHandler;

    @Mock
    private BindingResult bindingResult;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void givenValidationException_whenHandlingValidationErrors_thenReturnsBadRequestResponse() {
        // Given
        FieldError fieldError = new FieldError("objectName", "fieldName", "Field error message");
        List<FieldError> fieldErrors = Arrays.asList(fieldError);
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException validationException = new MethodArgumentNotValidException(null, bindingResult);

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleValidationErrors(validationException);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());

        ErrorResponse responseBody = response.getBody();
        assertEquals(HttpStatus.BAD_REQUEST.value(), responseBody.getStatus());
        assertEquals("Field error message", responseBody.getErrors().get(0));
    }

    @Test
    void givenGeneralRuntimeException_whenHandlingGeneralExceptions_thenReturnsInternalServerErrorResponse() {
        // Given
        RuntimeException runtimeException = new RuntimeException("General error message");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleGeneralExceptions(runtimeException);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponse responseBody = response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.getStatus());
        assertEquals("General error message", responseBody.getErrors().get(0));
    }

    @Test
    void givenRuntimeException_whenHandlingRuntimeExceptions_thenReturnsInternalServerErrorResponse() {
        // Given
        RuntimeException runtimeException = new RuntimeException("Runtime error message");

        // When
        ResponseEntity<ErrorResponse> response = globalExceptionHandler.handleRuntimeExceptions(runtimeException);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());

        ErrorResponse responseBody = response.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), responseBody.getStatus());
        assertEquals("Runtime error message", responseBody.getErrors().get(0));
    }
}
