package com.read.readbibleservice.controller;

import com.read.readbibleservice.config.jwt.JwtTokenUtil;
import com.read.readbibleservice.exception.UserCustomException;
import com.read.readbibleservice.model.ApiResponse;
import com.read.readbibleservice.model.Login;
import com.read.readbibleservice.model.User;
import com.read.readbibleservice.service.AuthService;
import io.jsonwebtoken.impl.DefaultClaims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtTokenUtil jwtTokenUtil;

  @Autowired
  private UserDetailsService userDetailsService;

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @RequestMapping(value = "/token", method = RequestMethod.POST)
  public String generateJwtToken(@RequestBody Login login) {
    this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
    UserDetails userDetails = userDetailsService.loadUserByUsername(login.getUsername());
    authService.checkIfUserRegisteredByEmail(login.getUsername());
    return jwtTokenUtil.generateToken(userDetails);
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

  @RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
  public String refreshtoken(HttpServletRequest request) throws Exception {
    // From the HttpRequest get the claims
    DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

    Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
    return jwtTokenUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());

  }

  public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
    Map<String, Object> expectedMap = new HashMap<String, Object>();
    for (Map.Entry<String, Object> entry : claims.entrySet()) {
      expectedMap.put(entry.getKey(), entry.getValue());
    }
    return expectedMap;
  }

}
