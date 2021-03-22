package com.read.readbibleservice.controller;

import com.read.readbibleservice.model.Profile;
import com.read.readbibleservice.service.ProfileService;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("api/admin/profile")
public class AdminProfileController {

    private final ProfileService profileService;

    public AdminProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @GetMapping
    public Mono<List<Profile>> getProfiles() {
        return profileService.getProfiles();
    }

    @GetMapping("{username}")
    public Mono<Profile> getProfile(@PathVariable String username) {
        return profileService.getProfileDetail(username);
    }

    @PostMapping(value="", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveProfiles(@RequestParam(name ="file",required=false) MultipartFile file,
                               @RequestPart(name ="profile",required=true) Profile profile) {
        return profileService.saveProfile(profile, file);
    }

}
