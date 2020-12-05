package com.read.readbibleservice.service;

import com.read.readbibleservice.model.ApiResponse;
import com.read.readbibleservice.model.User;
import org.springframework.web.servlet.ModelAndView;

public interface AuthService {

    ApiResponse registerUser(User user);

    ModelAndView confirmUser(String token);

    void checkIfUserRegisteredByEmail(String userName);

    ApiResponse sendConfirmationLink(String username);
}
