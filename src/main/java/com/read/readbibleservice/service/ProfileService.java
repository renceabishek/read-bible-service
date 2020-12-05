package com.read.readbibleservice.service;

import com.read.readbibleservice.model.Profile;
import reactor.core.publisher.Mono;

public interface ProfileService {

    Mono<Profile> getProfileDetail(String name);

}
