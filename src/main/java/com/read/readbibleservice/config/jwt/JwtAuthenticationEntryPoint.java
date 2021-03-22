package com.read.readbibleservice.config.jwt;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

import static com.read.readbibleservice.exception.ErrorCode.TOKEN_EXPIRED;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint, Serializable {

    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        String expired = (String) request.getAttribute("expired");
        if (expired != null) {
            response.sendError(TOKEN_EXPIRED.getHttpCode(), expired);
        } else {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Login details");
        }
    }
}