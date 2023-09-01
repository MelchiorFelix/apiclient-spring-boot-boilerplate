package com.diariodeumdev.apiclientspringbootboilerplate.domain.service;

import com.diariodeumdev.apiclientspringbootboilerplate.domain.model.User;
import lombok.Generated;

@Generated
public interface TokenService {

    String generateToken(User user);

    String validateToken(String token);
}
