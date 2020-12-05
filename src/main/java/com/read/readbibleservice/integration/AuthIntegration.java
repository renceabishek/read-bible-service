package com.read.readbibleservice.integration;

import com.read.readbibleservice.model.FirebaseKey;
import com.read.readbibleservice.model.UpdateUser;
import com.read.readbibleservice.model.User;
import reactor.core.publisher.Mono;

import java.util.HashMap;

public interface AuthIntegration {

  Mono<HashMap<String, User>> findUserByUserName(String userName);

  HashMap<String, User> findUserByEmail(String email);

  HashMap<String, User> findUserByToken(String token);

  Mono<User> findUserByUniqueId(String uniqueId);

  FirebaseKey saveUser(User user);

  Void updateEnableforUser(UpdateUser user, String uniqueKey);

  Void updateUser(User user, String uniqueKey);
}
