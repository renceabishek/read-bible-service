package com.read.readbibleservice.integration;

import com.google.cloud.storage.Acl;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.firebase.cloud.StorageClient;
import com.google.firebase.database.DatabaseReference;
import com.read.readbibleservice.config.properties.FirebaseDbProperties;
import com.read.readbibleservice.config.properties.FirebaseJsonProperties;
import com.read.readbibleservice.config.properties.FirebaseStorageProperties;
import com.read.readbibleservice.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.HashMap;

@Service
public class ProfileIntegrationImp implements ProfileIntegration {

    @Autowired
    @Qualifier("fbPersist")
    private DatabaseReference mainDatabaseReference;

    private final WebClient webClientFirebase;
    private final FirebaseJsonProperties firebaseJsonProperties;
    private final FirebaseStorageProperties firebaseStorageProperties;
    private final FirebaseDbProperties firebaseDbProperties;

    public ProfileIntegrationImp(WebClient webClientFirebase, FirebaseJsonProperties firebaseJsonProperties, FirebaseStorageProperties firebaseStorageProperties, FirebaseDbProperties firebaseDbProperties) {
        this.webClientFirebase = webClientFirebase;
        this.firebaseJsonProperties = firebaseJsonProperties;
        this.firebaseStorageProperties = firebaseStorageProperties;
        this.firebaseDbProperties = firebaseDbProperties;
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

    @Override
    public Mono<HashMap<String, Profile>> getProfiles() {

        return webClientFirebase.get()
                .uri(uriBuilder -> uriBuilder.path(firebaseJsonProperties.getLogin() + ".json")
                        .build())
                .retrieve()
                .onStatus(HttpStatus::isError, resp -> resp.createException()
                        .map(Exception::new)
                        .flatMap(Mono::error))
                .bodyToMono(new ParameterizedTypeReference<HashMap<String, Profile>>() {
                });
    }

    @Override
    public String saveProfilePic(MultipartFile file, String name) {
        Bucket bucket = StorageClient.getInstance().bucket();
        Blob blob=null;
        try {
            blob=bucket.create(firebaseStorageProperties.getProfile()+name, file.getInputStream(),
                    Bucket.BlobWriteOption.userProject(firebaseDbProperties.getName()));
            blob.getStorage().createAcl(blob.getBlobId(), Acl.of(Acl.User.ofAllUsers(), Acl.Role.READER));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return blob.getMediaLink();
    }

    @Override
    public String saveProfile(Profile profile) {
        DatabaseReference postsRef = mainDatabaseReference.child(firebaseJsonProperties.getLogin());
        DatabaseReference newPostRef = postsRef.push();
        newPostRef.setValueAsync(profile);
        return newPostRef.getKey();
    }


}
