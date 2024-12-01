package com.juan_zubiri.monitoreo.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private static final Logger logger = LoggerFactory.getLogger(CustomAuthenticationEntryPoint.class);

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        Throwable cause = authException.getCause();

        if (cause instanceof EmptyDatabaseException) {
            // log error para EmptyDatabaseException
            logger.error("Error: La base de datos está vacía. Cree un usuario para continuar.");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"La base de datos está vacía. Cree un usuario para continuar.\"}");
        } else {
            // log error usuario no autenticado
            logger.error("Error: Usuario no autenticado.");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\": \"Usuario no autenticado.\"}");
        }
    }
}


