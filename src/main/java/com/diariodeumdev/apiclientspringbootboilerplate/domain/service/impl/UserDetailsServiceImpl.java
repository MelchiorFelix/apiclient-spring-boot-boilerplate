package com.diariodeumdev.apiclientspringbootboilerplate.domain.service.impl;

import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.request.AuthenticationRequest;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.ErrorResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.application.dto.response.TokenResponse;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.User;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.UserRole;
import com.diariodeumdev.apiclientspringbootboilerplate.domain.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.util.Collections;

import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.ACCOUNT_NOT_FOUND;
import static com.diariodeumdev.apiclientspringbootboilerplate.infrastructure.utils.Constants.USER_IN_DATABASE;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final TokenServiceImpl tokenService;

    @Autowired
    public UserDetailsServiceImpl(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            TokenServiceImpl tokenService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByLogin(username).orElseThrow();
    }

    @Transactional
    public ResponseEntity register(AuthenticationRequest request) {
        if(!this.userRepository.findByLogin(request.getLogin()).isEmpty()) {
            return ResponseEntity
                    .status(HttpStatus.CONFLICT)
                    .body(new ErrorResponse(
                            new Timestamp(System.currentTimeMillis()),
                            HttpServletResponse.SC_CONFLICT,
                            Collections.singletonList(USER_IN_DATABASE))
                    );

        }

        String encryptedPassword = new BCryptPasswordEncoder().encode(request.getPassword());
        this.userRepository.save(new User(request.getLogin(),encryptedPassword, UserRole.USER));

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    public ResponseEntity authenticate(AuthenticationRequest request) {
        try {
            var userOptional = userRepository.findByLogin(request.getLogin());

            if (userOptional.isPresent()) {
                UserDetails userDetails = userOptional.get();
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                request.getLogin(),
                                request.getPassword()));
                var jwtToken = tokenService.generateToken((User) userDetails);
                return ResponseEntity.ok(new TokenResponse(jwtToken));
            } else {
                return unauthorizedResponse();
            }
        } catch (AuthenticationException authenticationException) {
            return unauthorizedResponse();
        }
    }

    private ResponseEntity unauthorizedResponse() {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .body(new ErrorResponse(
                        new Timestamp(System.currentTimeMillis()),
                        HttpServletResponse.SC_UNAUTHORIZED,
                        Collections.singletonList(ACCOUNT_NOT_FOUND))
                );
    }
}
