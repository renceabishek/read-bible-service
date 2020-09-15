package com.read.readbibleservice.integration;

import com.read.readbibleservice.config.properties.FirebaseJsonProperties;
import com.read.readbibleservice.model.Login;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class AuthIntegrationImp implements AuthIntegration {

  private final WebClient webClientFirebase;
  private final FirebaseJsonProperties firebaseJsonProperties;

  public AuthIntegrationImp(WebClient webClientFirebase, FirebaseJsonProperties firebaseJsonProperties) {
    this.webClientFirebase = webClientFirebase;
    this.firebaseJsonProperties = firebaseJsonProperties;
  }

  @Override
  public Mono<Login> checkUsernamePassword(String username, String password) {
    return webClientFirebase.get()
        .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin()+".json")
            .build())
        .retrieve()
        .onStatus(HttpStatus::isError, resp -> resp.createException()
            .map(Exception::new)
            .flatMap(Mono::error))
        .bodyToMono(Login.class);
  }
}
