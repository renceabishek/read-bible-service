package com.read.readbibleservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.read.readbibleservice.config.properties.UrlProperties;
import com.read.readbibleservice.config.security.ApplicationRole;
import com.read.readbibleservice.exception.ErrorCode;
import com.read.readbibleservice.exception.UserCustomException;
import com.read.readbibleservice.integration.AuthIntegration;
import com.read.readbibleservice.model.ApiResponse;
import com.read.readbibleservice.model.UpdateUser;
import com.read.readbibleservice.model.User;
import com.read.readbibleservice.util.FlagUtils;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.util.*;

import static com.read.readbibleservice.config.security.ApplicationRole.ADMIN;
import static com.read.readbibleservice.config.security.ApplicationRole.USER;

@Service
public class AuthServiceImp implements UserDetailsService, AuthService {

    private final AuthIntegration authIntegration;

    @Autowired
    private EmailUtilityService emailUtilityService;

    @Autowired
    private UrlProperties urlProperties;

    public AuthServiceImp(AuthIntegration authIntegration) {
        this.authIntegration = authIntegration;
    }

    @Autowired
    ObjectMapper objectMapper;

    @SneakyThrows
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        HashMap<String, User> hmUser = null;
        if (username.contains("@")) {
            hmUser = authIntegration.findUserByEmail(username);
        } else {
            hmUser = authIntegration.findUserByUserName(username).block();
        }
        User user = hmUser.values().stream().map(v -> objectMapper.convertValue(v, User.class)).findAny()
                .orElse(null);

        if (user == null) {
            throw new UsernameNotFoundException("UserName Not found!");
        } else {
            FlagUtils.IS_ENABLED = user.isEnabled();
            return new org.springframework.security.core.userdetails
                    .User(username.contains("@") ? user.getEmail() : user.getUsername(), user.getPassword(),
                    getAuthority(user.getRole()));
        }
    }

    private Set<SimpleGrantedAuthority> getAuthority(String role) {
        if(role.contains("ADMIN")) {
            return ADMIN.getAuthorities();
        } else {
            return USER.getAuthorities();
        }
    }

    @Override
    public ApiResponse registerUser(User user) {
        if (authIntegration.findUserByEmail(user.getEmail()).isEmpty()) {

            user.setEnabled(false);
            user.setConfirmationToken(UUID.randomUUID().toString());
            user.setConfirmationDateTime(LocalDateTime.now());
            user.setUsername(user.getEmail().split("@")[0]);
            user.setRole("ROLE_USER");
            authIntegration.saveUser(user);

            sendMail(user.getEmail(), user.getConfirmationToken());

            return new ApiResponse<>(20001, "User Registered", null);
        } else {
            throw new UserCustomException("User Already registered", ErrorCode.USER_ALREADY_REGISTERED);
        }
    }

    private void sendMail(String email, String token) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(email);
        mailMessage.setSubject("Complete Registration!");
        mailMessage.setText("To confirm your account, please click here : "
                + urlProperties.getIntegration() + "/auth/confirm-account?token=" + token);

        emailUtilityService.sendEmail(mailMessage);
    }

    @Override
    public ModelAndView confirmUser(String token) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("registration-confirmation");
        modelAndView.addObject("link", urlProperties.getFe());

        HashMap<String, User> hmUser = authIntegration.findUserByToken(token);
        boolean isTokenAvailable = !hmUser.isEmpty();
        if (isTokenAvailable) {
            User user = hmUser.values().stream().findAny().get();
            String uniqueId = hmUser.keySet().stream().findAny().get();
            modelAndView.addObject("username", user.getName());
            if (!user.isEnabled()) {
                if (LocalDateTime.now().minusHours(24).isBefore(user.getConfirmationDateTime())) {
                    authIntegration.updateEnableforUser(new UpdateUser(true), uniqueId);
                    modelAndView.addObject("message", "Registered Successfully! Now you can login");
                } else {
                    modelAndView.addObject("message", "This link is expired. Try login again to get new confirmation link!");
                }
            } else {
                modelAndView.addObject("message", "You've completed your registration already! You can login");
            }
        } else {
            modelAndView.addObject("message", "Your confirmation link is not correct! Please verify again!");
        }
        return modelAndView;
    }

    @Override
    public void checkIfUserRegisteredByEmail(String userName) {
        if (!FlagUtils.IS_ENABLED) {
            throw new UserCustomException("User Not yet Confirmed by Email", ErrorCode.USER_NOT_CONFIRMED_BY_EMAIL);
        }
    }

    @Override
    public ApiResponse sendConfirmationLink(String username) {
        HashMap<String, User> hmUser = null;
        if (username.contains("@")) {
            hmUser = authIntegration.findUserByEmail(username);
        } else {
            hmUser = authIntegration.findUserByUserName(username).block();
        }
        User user = hmUser.values().stream().map(v -> objectMapper.convertValue(v, User.class)).findAny()
                .orElse(null);

        if (user == null) {
            throw new UserCustomException("UserName Not found!", ErrorCode.USER_NAME_NOT_FOUND);
        } else {
            String uniqueId = hmUser.keySet().stream().findAny().get();
            sendMail(user.getEmail(), user.getConfirmationToken());
            user.setConfirmationDateTime(LocalDateTime.now());
            authIntegration.updateUser(user, uniqueId);
            return new ApiResponse<>(200, "Confirmation Link sent successfully", null);
        }
    }
}
