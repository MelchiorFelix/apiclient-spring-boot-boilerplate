package com.diariodeumdev.apiclientspringbootboilerplate.application.controller;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.ClientService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/clients")
@SecurityRequirement(name = "bearerAuth")
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
    public ResponseEntity<Client> insertClient(@Valid @RequestBody ClientRequest clientRequest){
        return _clientService.save(clientRequest);
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deleteClientById(@PathVariable Long id){
        return _clientService.deleteClientById(id);
    }

    @PutMapping("{id}")
    public ResponseEntity<Client> updateClient(@PathVariable Long id, @Valid @RequestBody Client client){
        return _clientService.updateClient(id, client);
    }

    @GetMapping
    public ResponseEntity find(ClientRequest filter){
        return _clientService.find(filter);
    }
}
