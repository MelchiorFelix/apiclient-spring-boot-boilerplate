package com.diariodeumdev.apiclientspringbootboilerplate.repository;

import com.diariodeumdev.apiclientspringbootboilerplate.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByName(String name);
}
