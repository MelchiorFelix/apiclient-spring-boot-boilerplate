package com.diariodeumdev.apiclientspringbootboilerplate;

import com.diariodeumdev.apiclientspringbootboilerplate.model.Client;
import com.diariodeumdev.apiclientspringbootboilerplate.repository.ClientRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class InitialInsert {

    private final ClientRepository _clientRepository;

    public InitialInsert(ClientRepository clientRepository){
        _clientRepository = clientRepository;
    }

    @Bean
    public CommandLineRunner init(){
        return args -> {
            if(_clientRepository.count() == 0)
                _clientRepository.save(new Client(1l,"John Doe"));
        };
    }
}
