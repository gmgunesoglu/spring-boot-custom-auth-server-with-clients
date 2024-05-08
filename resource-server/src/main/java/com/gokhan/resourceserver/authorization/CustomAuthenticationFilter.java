package com.gokhan.resourceserver.authorization;

import com.gokhan.resourceserver.dto.RequestDto;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {

    private final CustomAuthenticationProvider provider;
    private final RestTemplate restTemplate = new RestTemplate();

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            RequestDto requestDto = RequestDto.builder()
                    .uri(request.getRequestURI())
                    .method(request.getMethod())
                    .token(token).build();

            ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8000/authorizations/check", requestDto, String.class);
            Authentication authentication;
            if (result.getStatusCode() == HttpStatus.OK){
                authentication = provider.authenticate(new CustomAuthentication(true, "headerKey"));
            }else{
                authentication = provider.authenticate(new CustomAuthentication(false, "headerKey"));
            }
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request, response);
    }
}
