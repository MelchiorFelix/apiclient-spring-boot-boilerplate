package com.diariodeumdev.apiclientspringbootboilerplate.domain;


import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.ClientRepository;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
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
        try (MockedStatic<MockitoAnnotations> mockedStatic = Mockito.mockStatic(MockitoAnnotations.class)) {
            mockedStatic.when(MockitoAnnotations::openMocks).thenReturn(this);
            clientService = new ClientServiceImpl(clientRepository);
        }
    }

    @Test
    void save_ValidClientRequest_ReturnsCreatedResponse() {
        ClientRequest clientRequest = new ClientRequest("John Doe");
        Client savedClient = new Client();
        when(clientRepository.save(any())).thenReturn(savedClient);

        ResponseEntity<Client> response = clientService.save(clientRequest);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(savedClient, response.getBody());
    }

    @Test
    void deleteClientById_ExistingId_ReturnsNoContentResponse() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        ResponseEntity<Void> response = clientService.deleteClientById(clientId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteClientById_NonExistingId_ReturnsNotFoundResponse() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        ResponseEntity<Void> response = clientService.deleteClientById(clientId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void getClientById_ExistingId_ReturnsOkResponse() {
        Long clientId = 1L;
        Client client = new Client();
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        ResponseEntity<Client> response = clientService.getClientById(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(client, response.getBody());
    }

    @Test
    void getClientById_NonExistingId_ReturnsNotFoundResponse() {
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        ResponseEntity<Client> response = clientService.getClientById(clientId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void updateClient_ExistingId_ReturnsOkResponse() {
        Long clientId = 1L;
        Client updatedClient = new Client();
        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.save(any())).thenReturn(updatedClient);

        ResponseEntity<Client> response = clientService.updateClient(clientId, updatedClient);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
    }

    @Test
    void updateClient_NonExistingId_ReturnsNotFoundResponse() {
        Long clientId = 1L;
        Client updatedClient = new Client();
        when(clientRepository.existsById(clientId)).thenReturn(false);

        ResponseEntity<Client> response = clientService.updateClient(clientId, updatedClient);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void find_ValidFilter_ReturnsOkResponse() {
        ClientRequest filter = new ClientRequest("FilterName");
        when(clientRepository.findAll((Sort) any())).thenReturn(List.of(new Client()));

        ResponseEntity<List<Client>> response = clientService.find(filter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    void find_EmptyFilter_ReturnsOkResponseWithEmptyList() {
        ClientRequest filter = new ClientRequest("");
        when(clientRepository.findAll()).thenReturn(List.of(new Client()));

        ResponseEntity<List<Client>> response = clientService.find(filter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().isEmpty());
    }
}
