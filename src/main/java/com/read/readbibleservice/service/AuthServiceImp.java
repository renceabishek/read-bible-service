package com.read.readbibleservice.service;

import com.read.readbibleservice.integration.AuthIntegration;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class AuthServiceImp implements AuthService {

  private final AuthIntegration authIntegration;

  public AuthServiceImp(AuthIntegration authIntegration) {
    this.authIntegration = authIntegration;
  }

  @Override
  public Mono<Boolean> checkUsernamePassword(String username, String password) {

   return authIntegration.checkUsernamePassword(username, password)
        .map(login -> login.getUsername().equals(username) && login.getPassword().equals(password));
  }
}
