package com.diariodeumdev.apiclientspringbootboilerplate;

import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.List;

@Configuration
public class InitialInsert {

    private final ClientRepository _clientRepository;

    public InitialInsert(ClientRepository clientRepository){
        _clientRepository = clientRepository;
    }

    @Bean
    public CommandLineRunner init(){
        return args -> {
            if (_clientRepository.count() == 0) {
                List<Client> clientsToInsert = Arrays.asList(
                        new Client(1L, "John Doe"),
                        new Client(2L, "Jane Doe")
                );

                _clientRepository.saveAll(clientsToInsert);
            }
        };
    }
}
