package com.diariodeumdev.apiclientspringbootboilerplate.domain;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.ClientRepository;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    private ClientServiceImpl clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientRepository);
    }

    @Test
    void givenValidClientRequest_whenSavingClient_thenReturnsCreatedResponse() {
        // Given
        ClientRequest clientRequest = new ClientRequest("John Doe");
        Client savedClient = new Client();
        when(clientRepository.save(any())).thenReturn(savedClient);

        // When
        ResponseEntity<Client> response = clientService.save(clientRequest);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedClient, response.getBody());
    }

    @Test
    void givenExistingClientId_whenDeletingClient_thenReturnsNoContentResponse() {
        // Given
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // When
        ResponseEntity<Void> response = clientService.deleteClientById(clientId);

        // Then
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void givenNonExistingClientId_whenDeletingClient_thenReturnsNotFoundResponse() {
        // Given
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // When
        ResponseEntity<Void> response = clientService.deleteClientById(clientId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    // ... Other tests follow the same pattern ...

    @Test
    void givenValidFilter_whenFindingClients_thenReturnsOkResponse() {
        // Given
        ClientRequest filter = new ClientRequest("FilterName");
        when(clientRepository.findAll((Sort) any())).thenReturn(List.of(new Client()));

        // When
        ResponseEntity<List<Client>> response = clientService.find(filter);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void givenEmptyFilter_whenFindingClients_thenReturnsOkResponseWithEmptyList() {
        // Given
        ClientRequest filter = new ClientRequest("");
        when(clientRepository.findAll()).thenReturn(List.of(new Client()));

        // When
        ResponseEntity<List<Client>> response = clientService.find(filter);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}
