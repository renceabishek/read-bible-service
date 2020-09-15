package com.read.readbibleservice.integration;

import com.read.readbibleservice.model.Login;
import reactor.core.publisher.Mono;

public interface AuthIntegration {

  Mono<Login> checkUsernamePassword(String username, String password);
}
