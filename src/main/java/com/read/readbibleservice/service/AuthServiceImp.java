package com.read.readbibleservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.integration.AuthIntegration;
import com.read.readbibleservice.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AuthServiceImp implements UserDetailsService, AuthService {

    private final AuthIntegration authIntegration;

    public AuthServiceImp(AuthIntegration authIntegration) {
        this.authIntegration = authIntegration;
    }

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = authIntegration.getUser(username).map(f -> f.values().stream().map(v -> {
            return objectMapper.convertValue(v, User.class);
        }).findAny().orElse(null)).block();

        if (user == null) {
            throw new UsernameNotFoundException("UserName Not found!");
        }
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority(user.getRole()));
    }

    private List<SimpleGrantedAuthority> getAuthority(String role) {
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }
}
