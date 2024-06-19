package com.gokhan.authserver.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {

        // Authorization başlığını kontrol et
        String authHeader = request.getHeader("Authorization");

//        if (authHeader != null && authHeader.startsWith("Bearer ")) {
//            // Authorization başlığı yoksa veya Bearer ile başlamıyorsa
//            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized - Token present but invalid");
//        }
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Authorization başlığı yoksa veya Bearer ile başlamıyorsa
            response.sendRedirect("/login");
        } else {
            // Authorization başlığı varsa
            response.sendError(CustomHttpServletResponse.SC_REFRESH_TOKEN, "Unauthorized - Token present but invalid");
        }
    }
}