package com.read.readbibleservice.integration;

import com.read.readbibleservice.model.Profile;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public interface ProfileIntegration {
    Mono<HashMap<String, Profile>> getProfileDetail(String name);

}
