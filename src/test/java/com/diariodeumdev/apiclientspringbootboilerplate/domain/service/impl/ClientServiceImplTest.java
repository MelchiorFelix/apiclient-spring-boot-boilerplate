package com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.ClientRepository;
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

    @Mock
    private ServiceUtilsImpl serviceUtils;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientService = new ClientServiceImpl(clientRepository, serviceUtils);
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
        when(serviceUtils.notFoundResponse()).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        // When
        ResponseEntity<Void> response = clientService.deleteClientById(clientId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

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

    @Test
    void givenExistingClientIdAndValidClient_whenUpdatingClient_thenReturnsOkResponse() {
        // Given
        Long clientId = 1L;
        Client updatedClient = new Client("Updated Client");
        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.save(updatedClient)).thenReturn(updatedClient);

        // When
        ResponseEntity<Client> response = clientService.updateClient(clientId, updatedClient);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
    }

    @Test
    void givenNonExistingClientIdAndValidClient_whenUpdatingClient_thenReturnsNotFoundResponse() {
        // Given
        Long clientId = 1L;
        Client updatedClient = new Client("Updated Client");
        when(clientRepository.existsById(clientId)).thenReturn(false);
        when(serviceUtils.notFoundResponse()).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        // When
        ResponseEntity<Client> response = clientService.updateClient(clientId, updatedClient);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void givenExistingClientId_whenGettingClientById_thenReturnsOkResponse() {
        // Given
        Long clientId = 1L;
        Client existingClient = new Client("Existing Client");
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(existingClient));

        // When
        ResponseEntity<Client> response = clientService.getClientById(clientId);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(existingClient, response.getBody());
    }

    @Test
    void givenNonExistingClientId_whenGettingClientById_thenReturnsNotFoundResponse() {
        // Given
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());
        when(serviceUtils.notFoundResponse()).thenReturn(ResponseEntity.status(HttpStatus.NOT_FOUND).build());

        // When
        ResponseEntity<Client> response = clientService.getClientById(clientId);

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

}
