package com.gokhan.authserver.config;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    private final JwtDecoder jwtDecoder;

    public CustomAuthenticationEntryPoint(JwtDecoder jwtDecoder) {
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    public void commence(
            HttpServletRequest request,
            HttpServletResponse response,
            AuthenticationException authException)
            throws IOException, ServletException {

        // Authorization başlığını kontrol et
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            // Authorization başlığı yoksa veya Bearer ile başlamıyorsa
            response.sendRedirect("/login");
        } else {
            // Authorization başlığı varsa
            String accessToken = authHeader.substring(7);
            try{
                Jwt jwt = jwtDecoder.decode(accessToken);
            }catch (Exception e){
                System.out.println(e.getMessage());
                if (e.getMessage().startsWith("An error occurred while attempting to decode the Jwt: Jwt expired at")){
                    response.sendError(CustomHttpServletResponse.SC_REFRESH_TOKEN, "Access token expired.");
                }
            }
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid access token.");
        }
    }
}