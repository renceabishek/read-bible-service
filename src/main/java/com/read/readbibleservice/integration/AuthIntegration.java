package com.read.readbibleservice.integration;

import com.read.readbibleservice.model.Integration.UserIntegration;
import com.read.readbibleservice.model.Login;
import com.read.readbibleservice.model.User;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public interface AuthIntegration {

  Mono<HashMap<String, User>> getUser(String userName);
}
