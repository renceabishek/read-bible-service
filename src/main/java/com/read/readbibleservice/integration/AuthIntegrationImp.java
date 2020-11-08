package com.read.readbibleservice.integration;

import com.read.readbibleservice.config.properties.FirebaseJsonProperties;
import com.read.readbibleservice.model.User;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class AuthIntegrationImp implements AuthIntegration {

    private final WebClient webClientFirebase;
    private final RestTemplate restTemplateFirebase;
    private final FirebaseJsonProperties firebaseJsonProperties;

    public AuthIntegrationImp(WebClient webClientFirebase, FirebaseJsonProperties firebaseJsonProperties,
                              RestTemplate restTemplateFirebase) {
        this.webClientFirebase = webClientFirebase;
        this.firebaseJsonProperties = firebaseJsonProperties;
        this.restTemplateFirebase = restTemplateFirebase;
    }

    @Override
    public Mono<HashMap<String, User>> getUser(String userName) {
        return webClientFirebase.get()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() + ".json")
                        .queryParam("orderBy", "\"username\"")
                        .queryParam("equalTo", "\"" + userName + "\"")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, User>>() {
                });
    }
}
