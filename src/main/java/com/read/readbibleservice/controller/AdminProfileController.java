package com.read.readbibleservice.controller;

import com.read.readbibleservice.model.Profile;
import com.read.readbibleservice.service.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("api/admin/profile")
public class AdminProfileController {

    private final ProfileService profileService;

    public AdminProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping("{username}")
    public Mono<Profile> getProfile(@PathVariable String username) {
        return profileService.getProfileDetail(username);
    }

}
