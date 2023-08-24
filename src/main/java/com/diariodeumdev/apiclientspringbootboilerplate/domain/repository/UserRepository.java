package com.diariodeumdev.apiclientspringbootboilerplate.domain.repository;

import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {
    Optional<UserDetails> findByLogin(String login);
}
