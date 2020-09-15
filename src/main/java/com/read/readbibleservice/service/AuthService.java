package com.read.readbibleservice.service;

import reactor.core.publisher.Mono;

public interface AuthService {

  Mono<Boolean> checkUsernamePassword(String username, String password);
}
