package com.read.readbibleservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.integration.ProfileIntegration;
import com.read.readbibleservice.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class ProfileServiceImp implements ProfileService {

    private final ProfileIntegration profileIntegration;

    public ProfileServiceImp(ProfileIntegration profileIntegration) {
        this.profileIntegration = profileIntegration;
    }

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public Mono<Profile> getProfileDetail(String name) {
        return profileIntegration.getProfileDetail(name).map(f -> f.values().stream().map(profile -> {
            return objectMapper.convertValue(profile, Profile.class);
        }).findAny().orElse(null));
    }
}
