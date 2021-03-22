package com.read.readbibleservice.integration;

import com.read.readbibleservice.model.Profile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.List;

public interface ProfileIntegration {
    Mono<HashMap<String, Profile>> getProfileDetail(String name);

    Mono<HashMap<String, Profile>> getProfiles();

    String saveProfilePic(MultipartFile file, String name);

    String saveProfile(Profile profile);
}
