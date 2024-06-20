//package com.gokhan.resourceserver.authorization;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//
//@Component
//@Slf4j
//public class CustomAuthenticationProvider implements AuthenticationProvider {
//
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
//        // kontrol burada
//
//        if (customAuthentication.isAuthenticated()) {
//            log.info("Authenticated user: {}", customAuthentication.getPrincipal());
//        }
//        return authentication;
//
////        throw new BadCredentialsException("Bad credentials");
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return CustomAuthentication.class.equals(authentication);
////        return CustomAuthentication.class.isAssignableFrom(authentication);
//    }
//}
