package com.read.readbibleservice.service;

import com.read.readbibleservice.model.Profile;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

public interface ProfileService {

    Mono<Profile> getProfileDetail(String name);

    Mono<List<Profile>> getProfiles();

    String saveProfile(Profile profile, MultipartFile file);
}
