package com.diariodeumdev.apiclientspringbootboilerplate.service;

import com.diariodeumdev.apiclientspringbootboilerplate.model.Client;
import org.springframework.http.ResponseEntity;

public interface ClientService {
    ResponseEntity<Void> deleteClientById(Long id);

    ResponseEntity<Client> updateClient(Long id, Client client);

    ResponseEntity find(Client filter);

    ResponseEntity<Client> getClientById(Long id);

    ResponseEntity<Client> save(Client client);
}
