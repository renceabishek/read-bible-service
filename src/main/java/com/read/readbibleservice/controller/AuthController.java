package com.read.readbibleservice.controller;

import com.read.readbibleservice.service.AuthService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @GetMapping(value = "/{username}/{password}")
  public Mono<Boolean> checkUsernamePassword(@PathVariable String username, @PathVariable String password) {
    return authService.checkUsernamePassword(username, password);
  }

}
