package com.read.readbibleservice.controller;

import com.read.readbibleservice.config.jwt.JwtTokenUtil;
import com.read.readbibleservice.exception.UserCustomException;
import com.read.readbibleservice.model.ApiResponse;
import com.read.readbibleservice.model.Login;
import com.read.readbibleservice.model.User;
import com.read.readbibleservice.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @RequestMapping(value = "/token", method = RequestMethod.POST)
  public String generateJwtToken(@RequestBody Login login) {
    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
    authService.checkIfUserRegisteredByEmail(login.getUsername());
    return jwtTokenUtil.generateToken(login);
  }

  @RequestMapping(value = "/register", method = RequestMethod.POST)
  public ApiResponse registerUser(@RequestBody User user) {
    return authService.registerUser(user);
  }

  @RequestMapping(value = "/confirm-account", method = {RequestMethod.GET, RequestMethod.POST})
  public ModelAndView confirmUser(@RequestParam("token") String confirmationToken) {
    return authService.confirmUser(confirmationToken);
  }

  @RequestMapping(value = "/confirmation-link", method = RequestMethod.POST)
  public ApiResponse sendConfirmationLink(@RequestBody User user) {
    return authService.sendConfirmationLink(user.getUsername());
  }

}
