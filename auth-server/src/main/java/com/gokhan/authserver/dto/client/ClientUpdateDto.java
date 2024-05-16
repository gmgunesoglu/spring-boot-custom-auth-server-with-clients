package com.gokhan.authserver.dto.client;

import lombok.Getter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.List;

@Getter
public class ClientUpdateDto {

    private String name;
    private String clientSecret;
    private String baseUrl;
    private List<AuthorizationGrantType> authorizationGrantTypes;
    private String redirectUris;
    private String postLogoutRedirectUris;
    private ClientAuthenticationMethod clientAuthenticationMethod;
}
