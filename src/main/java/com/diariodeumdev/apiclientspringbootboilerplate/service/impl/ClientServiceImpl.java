package com.diariodeumdev.apiclientspringbootboilerplate.service.impl;

import com.diariodeumdev.apiclientspringbootboilerplate.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.repository.ClientRepository;
import com.diariodeumdev.apiclientspringbootboilerplate.service.ClientService;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class ClientServiceImpl implements ClientService {

    private final ClientRepository _clientRepository;

    public ClientServiceImpl(ClientRepository clientRepository){
        _clientRepository = clientRepository;
    }

    @Transactional
    public ResponseEntity<Client> save(Client client) {
        return ResponseEntity.status(HttpStatus.CREATED).body(_clientRepository.save(client));
    }

    @Transactional
    public ResponseEntity<Void> deleteClientById(Long id) {
        var client = _clientRepository.findById(id);
        if(client.isPresent()){
            _clientRepository.delete(client.get());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Client> getClientById(Long id) {
        return _clientRepository
                .findById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Transactional
    public ResponseEntity<Client> updateClient(Long id, Client client) {
        if(_clientRepository.existsById(id)){
            client.setId(id);
            return ResponseEntity.ok(_clientRepository.save(client));
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity find(Client filter) {
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filter, matcher);
        return ResponseEntity.ok(_clientRepository.findAll(example));
    }
}
