package com.diariodeumdev.apiclientspringbootboilerplate.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
class ClientControllerTest {

    @Mock
    private ClientService clientService;

    private ClientController clientController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientController = new ClientController(clientService);
    }

    @Test
    void getClientById_ValidId_ReturnsClient() {
        Long clientId = 1L;
        Client client = new Client();
        when(clientService.getClientById(clientId)).thenReturn(ResponseEntity.ok(client));

        ResponseEntity<Client> response = clientController.getClientById(clientId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(client, response.getBody());
    }

    @Test
    void insertClient_ValidClient_ReturnsCreatedClient() {
        Client client = new Client();
        when(clientService.save(client)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(client));

        ResponseEntity<Client> response = clientController.insertClient(client);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(client, response.getBody());
    }

    @Test
    void deleteClientById_ValidId_ReturnsNoContent() {
        Long clientId = 1L;
        when(clientService.deleteClientById(clientId)).thenReturn(ResponseEntity.noContent().build());

        ResponseEntity<Void> response = clientController.deleteClientById(clientId);

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void updateClient_ValidIdAndClient_ReturnsUpdatedClient() {
        Long clientId = 1L;
        Client updatedClient = new Client();
        when(clientService.updateClient(eq(clientId), any())).thenReturn(ResponseEntity.ok(updatedClient));

        ResponseEntity<Client> response = clientController.updateClient(clientId, updatedClient);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(updatedClient, response.getBody());
    }

    @Test
    void find_ValidFilter_ReturnsClients() {
        Client filter = new Client();
        when(clientService.find(filter)).thenReturn(ResponseEntity.ok("List of clients"));

        var response = clientController.find(filter);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
