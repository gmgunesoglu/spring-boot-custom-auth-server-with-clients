package com.gokhan.authserver.authentication;

import org.springframework.security.oauth2.server.authorization.InMemoryOAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.security.oauth2.server.authorization.OAuth2TokenType;

public class CustomOAuth2AuthorizationService implements OAuth2AuthorizationService {
    private final InMemoryOAuth2AuthorizationService authorizationService;

    public CustomOAuth2AuthorizationService() {
        authorizationService = new InMemoryOAuth2AuthorizationService();
    }

    @Override
    public void save(OAuth2Authorization authorization) {       //aha bu iyiburadan auhorization ı tut
        authorizationService.save(authorization);
    }

    @Override
    public void remove(OAuth2Authorization authorization) {
        authorizationService.remove(authorization);
    }

    @Override
    public OAuth2Authorization findById(String id) {
        return authorizationService.findById(id);
    }

    @Override
    public OAuth2Authorization findByToken(String token, OAuth2TokenType tokenType) {
        return authorizationService.findByToken(token, tokenType);
    }
    
}
