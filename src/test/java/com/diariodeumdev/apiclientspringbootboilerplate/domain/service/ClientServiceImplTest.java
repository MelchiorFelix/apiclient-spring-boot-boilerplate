package com.diariodeumdev.apiclientspringbootboilerplate.domain.service;


import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.ClientRepository;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl.ClientServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class ClientServiceImplTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientServiceImpl clientService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSaveClient() {
        // Arrange
        ClientRequest clientRequest = new ClientRequest("John Doe");
        Client client = new Client();
        when(clientRepository.save(any(Client.class))).thenReturn(client);

        // Act
        ResponseEntity<Client> response = clientService.save(clientRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(client, response.getBody());
    }


    @Test
    public void testDeleteClientById() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // Act
        ResponseEntity<?> response = clientService.deleteClientById(clientId);

        // Assert
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(clientRepository, times(1)).delete(any());
    }

    @Test
    public void testDeleteClientByIdNotFound() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> response = clientService.deleteClientById(clientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testGetClientById() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(new Client()));

        // Act
        ResponseEntity<Client> response = clientService.getClientById(clientId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testGetClientByIdNotFound() {
        // Arrange
        Long clientId = 1L;
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<Client> response = clientService.getClientById(clientId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testUpdateClient() {
        // Arrange
        Long clientId = 1L;
        Client updatedClient = new Client();
        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.save(updatedClient)).thenReturn(updatedClient);

        // Act
        ResponseEntity<Client> response = clientService.updateClient(clientId, updatedClient);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(updatedClient, response.getBody());
    }

    @Test
    public void testUpdateClientNotFound() {
        // Arrange
        Long clientId = 1L;
        Client updatedClient = new Client();
        when(clientRepository.existsById(clientId)).thenReturn(false);

        // Act
        ResponseEntity<Client> response = clientService.updateClient(clientId, updatedClient);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void testFindClients() {
        // Arrange
        ClientRequest filter = new ClientRequest("John Doe");
        List<Client> clients = new ArrayList<>();
        when(clientRepository.findAll(Sort.unsorted())).thenReturn(clients);

        // Act
        ResponseEntity<List<Client>> response = clientService.find(filter);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(clients, response.getBody());
    }
}
