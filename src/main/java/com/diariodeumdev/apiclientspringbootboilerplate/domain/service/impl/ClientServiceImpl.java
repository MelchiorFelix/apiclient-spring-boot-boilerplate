package com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.ClientRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.ClientRepository;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.service.ClientService;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository _clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository) {
        _clientRepository = clientRepository;
    }

    @Transactional
    public ResponseEntity<Client> save(ClientRequest clientRequest) {
        var client = new Client();
        BeanUtils.copyProperties(clientRequest, client);
        return ResponseEntity.status(HttpStatus.CREATED).body(_clientRepository.save(client));
    }

    @Transactional
    public ResponseEntity deleteClientById(Long id) {
        var client = _clientRepository.findById(id);
        if (client.isPresent()) {
            _clientRepository.delete(client.get());
            return ResponseEntity.noContent().build();
        }
        return notFoundResponse();
    }

    public ResponseEntity<Client> getClientById(Long id) {
        return _clientRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(this::notFoundResponse);
    }

    @Transactional
    public ResponseEntity<Client> updateClient(Long id, Client client) {
        if (_clientRepository.existsById(id)) {
            client.setId(id);
            return ResponseEntity.ok(_clientRepository.save(client));
        }
        return notFoundResponse();
    }

    public ResponseEntity find(ClientRequest filter) {
        Client clientFilter = new Client(filter.getName());

        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example<Client> example = Example.of(clientFilter, matcher);
        List<Client> filteredClients = _clientRepository.findAll(example);

        return ResponseEntity.ok(filteredClients);
    }

    private ResponseEntity notFoundResponse() {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .build();
    }
}
