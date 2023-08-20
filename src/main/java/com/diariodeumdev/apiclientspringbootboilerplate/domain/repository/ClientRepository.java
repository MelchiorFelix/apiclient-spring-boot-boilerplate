package com.diariodeumdev.apiclientspringbootboilerplate.domain.repository;

import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Long> {
    boolean existsByName(String name);
}
