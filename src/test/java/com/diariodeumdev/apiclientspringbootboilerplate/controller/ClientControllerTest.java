package com.diariodeumdev.apiclientspringbootboilerplate.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.controller.ClientController;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.ClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.springframework.beans.BeanUtils;
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
        try (MockedStatic<Mockito> mockedStatic = Mockito.mockStatic(Mockito.class)) {
            mockedStatic.when(Mockito::mock).thenReturn(clientService);
            clientController = new ClientController(clientService);
        }
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
        ClientRequest clientRequest = new ClientRequest("John Doe");
        Client client = new Client();
        BeanUtils.copyProperties(clientRequest, client);
        when(clientService.save(client)).thenReturn(ResponseEntity.status(HttpStatus.CREATED).body(client));

        ResponseEntity<Client> response = clientController.insertClient(clientRequest);

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
