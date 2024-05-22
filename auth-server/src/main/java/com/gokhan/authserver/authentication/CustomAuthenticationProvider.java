package com.gokhan.authserver.authentication;

import com.gokhan.authserver.entity.User;
import com.gokhan.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

//@Component
@Slf4j
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        return token;
    }

    public CustomAuthentication authenticate(String username, String password) throws AuthenticationException {
        User user = userRepository.findByUsername(username).orElse(null);
        if (user == null) {
            log.info("User not found with username: {}", username);
        }else if (!passwordEncoder.matches(password, user.getPassword())) {
            log.info("Password didn't matched for:\nusername: {}\npassword: {}", username, password);
        }else{
            return new CustomAuthentication(user.getAuthorities());
        }
        // returns !authenticated
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }


//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        CustomAuthentication customAuthentication = (CustomAuthentication) authentication;
//        return customAuthentication;
//    }
//
//    public CustomAuthentication authenticate(String username, String password) throws AuthenticationException {
//        User user = userRepository.findByUsername(username).orElse(null);
//        if (user == null) {
//            log.info("User not found with username: {}", username);
//        }else if (!passwordEncoder.matches(password, user.getPassword())) {
//            log.info("Password didn't matched for:\nusername: {}\npassword: {}", username, password);
//        }else{
//            return new CustomAuthentication(user.getAuthorities());
//        }
//        // returns !authenticated
//        return null;
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return CustomAuthentication.class.equals(authentication);
//    }
}
