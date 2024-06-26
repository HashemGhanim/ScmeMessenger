package com.scme.messenger.config;

import java.io.IOException;
import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.scme.messenger.constants.ResponseConstants;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {

        log.error(request.getServletPath().toString());
        log.error(request.getHeader("Authorization").toString());
        log.error(request.toString());
        log.error(authException.toString());

        response.setContentType("application/json");

        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseBody = new JSONObject();
        responseBody.put("apiPath", request.getServletPath());
        responseBody.put("errorCode", ResponseConstants.STATUS_401);
        responseBody.put("errorMessage", ResponseConstants.MESSAGE_401);
        responseBody.put("errorTime", LocalDateTime.now());

        response.getWriter().write(responseBody.toString());
    }

}
