package com.read.readbibleservice.integration;

import com.google.firebase.database.DatabaseReference;
import com.read.readbibleservice.config.properties.FirebaseJsonProperties;
import com.read.readbibleservice.model.BibleData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.HashMap;

@Service
public class DataServiceIntegrationImp implements DataServiceIntegration {

    @Autowired
    @Qualifier("fbPersist")
    private DatabaseReference mainDatabaseReference;

    private final WebClient webClientFirebase;
    private final FirebaseJsonProperties firebaseJsonProperties;

    public DataServiceIntegrationImp(WebClient webClientFirebase, FirebaseJsonProperties firebaseJsonProperties) {
        this.webClientFirebase = webClientFirebase;
        this.firebaseJsonProperties = firebaseJsonProperties;
    }

    @Override
    public Mono<HashMap<String, BibleData>> fetchAllBibleData() {

        return webClientFirebase.get()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getBibledata() + ".json")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, BibleData>>() {
                });
    }

    @Override
    public String createBibleData(BibleData bibleData) {
        DatabaseReference postsRef = mainDatabaseReference.child(firebaseJsonProperties.getBibledata());
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValueAsync(bibleData);
        return newPostRef.getKey();
    }

    @Override
    public void updateBibleData(BibleData bibleData, String uniqueId) {

        webClientFirebase.put()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getBibledata()+"/"+uniqueId+".json")
                        .build())
                .bodyValue(bibleData)
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(Void.class).block();

    }

    @Override
    public void deleteBibleData(String uniqueId) {
            webClientFirebase
                    .delete()
                    .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getBibledata()+"/"+uniqueId+".json")
                            .build())
                    .retrieve()
                    .onStatus(HttpStatus::isError, resp -> resp.createException()
                            .map(Exception::new)
                            .flatMap(Mono::error))
                    .bodyToMono(Void.class).block();
    }
}
