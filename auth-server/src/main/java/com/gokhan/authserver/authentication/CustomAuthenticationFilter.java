//package com.gokhan.authserver.authentication;
//
//import com.gokhan.authserver.entity.Role;
//import com.gokhan.authserver.exceptionhandling.GlobalRuntimeException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.annotation.Order;
//import org.springframework.http.HttpStatus;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@Component
//@RequiredArgsConstructor
//public class CustomAuthenticationFilter extends OncePerRequestFilter {
//
//    private final CustomAuthenticationProvider authenticationProvider;
//
//    @Override
//    protected void doFilterInternal(
//            @NonNull HttpServletRequest request,
//            @NonNull HttpServletResponse response,
//            @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        String authorizationHeader = request.getHeader("Authorization");
//        if (authorizationHeader != null && authorizationHeader.startsWith("Basic ")) {
//
//            String base64Credentials = authorizationHeader.substring(6);
//            byte[] decodedBytes = java.util.Base64.getDecoder().decode(base64Credentials);
//            String credentials = new String(decodedBytes);
//            String[] userDetails = credentials.split(":", 2);
//            String username = userDetails[0];
//            String password = userDetails[1];
//            CustomAuthentication authentication = authenticationProvider.authenticate(username, password);
//            if (authentication == null) {
//                throw new GlobalRuntimeException("Unauthenticated request", HttpStatus.UNAUTHORIZED);
//            }
//            for (GrantedAuthority authority : authentication.getAuthorities()) {
//                if ("SUPER_USER".equals(authority.getAuthority())) {
//                    SecurityContextHolder.getContext().setAuthentication(authentication);
//                    filterChain.doFilter(request, response);
//                    return;
//                }
//            }
//            throw new GlobalRuntimeException("Unauthorized request", HttpStatus.UNAUTHORIZED);
//        }
//        filterChain.doFilter(request, response);
//    }
//}
