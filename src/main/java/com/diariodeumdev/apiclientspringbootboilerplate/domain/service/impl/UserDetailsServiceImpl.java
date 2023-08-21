package com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.ErrorResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.TokenResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.User;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.USER_EXISTS;
import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.USER_IN_DATABASE;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username).orElseThrow();
    }

    @Transactional
    public ResponseEntity register(AuthenticationRequest request) {
        if(!this.userRepository.findByLogin(request.login()).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(USER_EXISTS, USER_IN_DATABASE, 409));

        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.password());
        this.userRepository.save(new User(request.login(),encryptedPassword, UserRole.USER));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.login(),
                        request.password()));
        var user = userRepository.findByLogin(request.login()).orElseThrow();
        var jwtToken = tokenService.generateToken((User)user);
        return ResponseEntity.ok(new TokenResponse(jwtToken));
    }
}
