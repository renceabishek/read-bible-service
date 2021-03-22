package com.read.readbibleservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.integration.ProfileIntegration;
import com.read.readbibleservice.model.Profile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
        return profileIntegration.getProfileDetail(name).map(f -> f.values().stream()
                .map(profile -> objectMapper.convertValue(profile, Profile.class))
                .findAny()
                .orElse(null));
    }

    @Override
    public Mono<List<Profile>> getProfiles() {
        return profileIntegration.getProfiles()
                .map(monoProfile -> monoProfile.entrySet().stream()
                .map(k -> {
                    Profile profile = objectMapper.convertValue(k.getValue(), Profile.class);
                    return new Profile(k.getKey(), profile.getName(), profile.getRole());
                })
                .sorted(Comparator.comparing(Profile::getName)).collect(Collectors.toList())
        );
    }

    @Override
    public String saveProfile(Profile profile, MultipartFile file) {
        if(file!=null && !file.isEmpty()) {
            String profileUrl = profileIntegration.saveProfilePic(file, profile.getName());
            profile.setPicUrl(profileUrl);
            profile.setRole("ROLE_USER");
            profile.setLinked(true);
        }
        return profileIntegration.saveProfile(profile);
    }

}
