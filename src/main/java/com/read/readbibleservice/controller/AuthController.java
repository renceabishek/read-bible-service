package com.read.readbibleservice.controller;

import com.read.readbibleservice.config.jwt.JwtTokenUtil;
import com.read.readbibleservice.model.ApiResponse;
import com.read.readbibleservice.model.AuthToken;
import com.read.readbibleservice.model.Login;
import com.read.readbibleservice.model.User;
import com.read.readbibleservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  private AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @RequestMapping(value = "/token", method = RequestMethod.POST)
  public String generateJwtToken(@RequestBody Login login) {
    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
    return jwtTokenUtil.generateToken(login);
  }

}
