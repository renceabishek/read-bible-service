package com.read.readbibleservice.integration;

import com.read.readbibleservice.config.properties.FirebaseJsonProperties;
import com.read.readbibleservice.model.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class ProfileIntegrationImp implements ProfileIntegration {

    private final WebClient webClientFirebase;
    private final FirebaseJsonProperties firebaseJsonProperties;

    public ProfileIntegrationImp(WebClient webClientFirebase, FirebaseJsonProperties firebaseJsonProperties) {
        this.webClientFirebase = webClientFirebase;
        this.firebaseJsonProperties = firebaseJsonProperties;
    }

    @Override
    public Mono<HashMap<String, Profile>> getProfileDetail(String name) {
        return webClientFirebase.get()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() + ".json")
                        .queryParam("orderBy", "\"username\"")
                        .queryParam("equalTo", "\"" + name + "\"")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Profile>>() {
                });
    }



}
