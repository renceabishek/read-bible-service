package com.read.readbibleservice.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.config.properties.FirebaseJsonProperties;
import com.read.readbibleservice.model.FirebaseKey;
import com.read.readbibleservice.model.UpdateUser;
import com.read.readbibleservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @Autowired
    private ObjectMapper objectMapper;

    public AuthIntegrationImp(WebClient webClientFirebase, FirebaseJsonProperties firebaseJsonProperties,
                              RestTemplate restTemplateFirebase) {
        this.webClientFirebase = webClientFirebase;
        this.firebaseJsonProperties = firebaseJsonProperties;
        this.restTemplateFirebase = restTemplateFirebase;
    }

    @Override
    public Mono<HashMap<String, User>> findUserByUserName(String userName) {
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

    @Override
    public HashMap<String, User> findUserByEmail(String email) {
        return webClientFirebase.get()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() + ".json")
                        .queryParam("orderBy", "\"email\"")
                        .queryParam("equalTo", "\"" + email + "\"")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, User>>() {
                }).block();
    }

    @Override
    public HashMap<String, User> findUserByToken(String token) {
        return webClientFirebase.get()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() + ".json")
                        .queryParam("orderBy", "\"confirmationToken\"")
                        .queryParam("equalTo", "\"" + token + "\"")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, User>>() {
                }).block();
    }

    @Override
    public Mono<User> findUserByUniqueId(String uniqueId) {
        return webClientFirebase.get()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() +"/"+uniqueId+".json")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(User.class);
    }

    @Override
    public FirebaseKey saveUser(User user) {
        return webClientFirebase.post()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() +".json")
                        .build())
                .bodyValue(user)
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(FirebaseKey.class).block();
    }

    @Override
    public Void updateEnableforUser(UpdateUser user, String uniqueKey) {

        return webClientFirebase.patch()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() +"/"+uniqueKey+".json")
                        .build())
                .contentType(MediaType.valueOf("application/json-patch+json"))
                .bodyValue(user)
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(Void.class).block();
    }

    @Override
    public Void updateUser(User user, String uniqueKey) {
        return webClientFirebase.put()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() +"/"+uniqueKey+".json")
                        .build())
                .bodyValue(user)
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(Void.class).block();
    }
}
