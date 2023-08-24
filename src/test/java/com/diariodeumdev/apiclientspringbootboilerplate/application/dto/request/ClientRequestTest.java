package com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request;

import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class ClientRequestTest {

    private final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    void givenValidClientRequest_whenValidating_thenNoViolations() {
        // Given
        ClientRequest clientRequest = new ClientRequest("John Doe");

        // When
        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        // Then
        assertTrue(violations.isEmpty());
    }

    @Test
    void givenNameTooShortClientRequest_whenValidating_thenOneViolation() {
        // Given
        ClientRequest clientRequest = new ClientRequest("Jo");

        // When
        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 3 and 100", violations.iterator().next().getMessage());
    }

    @Test
    void givenNameTooLongClientRequest_whenValidating_thenOneViolation() {
        // Given
        StringBuilder longName = new StringBuilder();
        longName.append("a".repeat(101));
        ClientRequest clientRequest = new ClientRequest(longName.toString());

        // When
        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        // Then
        assertFalse(violations.isEmpty());
        assertEquals(1, violations.size());
        assertEquals("size must be between 3 and 100", violations.iterator().next().getMessage());
    }

    @Test
    void givenEmptyNameClientRequest_whenValidating_thenOneViolation() {
        // Given
        ClientRequest clientRequest = new ClientRequest("");

        // When
        Set<ConstraintViolation<ClientRequest>> violations = validator.validate(clientRequest);

        // Then
        assertFalse(violations.isEmpty());
    }
}
