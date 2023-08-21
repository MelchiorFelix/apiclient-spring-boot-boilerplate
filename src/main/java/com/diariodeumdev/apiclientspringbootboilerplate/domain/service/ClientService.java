package com.diariodeumdev.apiclientspringbootboilerplate.domain.service;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<Void> deleteClientById(Long id);

    ResponseEntity<Client> updateClient(Long id, Client client);

    ResponseEntity find(ClientRequest filter);

    ResponseEntity<Client> getClientById(Long id);

    ResponseEntity<Client> save(ClientRequest client);
}
