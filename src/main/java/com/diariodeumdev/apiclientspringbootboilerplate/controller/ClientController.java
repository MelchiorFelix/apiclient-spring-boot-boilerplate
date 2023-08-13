package com.diariodeumdev.apiclientspringbootboilerplate.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.service.ClientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
public class ClientController {

    private final ClientService _clientService;

    public ClientController(ClientService clientService){
        _clientService = clientService;
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<Client> getClientById(@PathVariable Long id){
        return _clientService.getClientById(id);
    }

    @PostMapping
    public ResponseEntity<Client> insertClient(@RequestBody Client client){
        return _clientService.save(client);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        return _clientService.deleteClientById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @RequestBody Client client){
        return _clientService.updateClient(id, client);
    }

    @GetMapping
    public ResponseEntity find(Client filter){
        return _clientService.find(filter);
    }
}
