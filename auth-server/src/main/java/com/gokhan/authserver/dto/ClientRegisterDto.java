package com.gokhan.authserver.dto;

import lombok.Getter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.List;
import java.util.Set;

@Getter
public class ClientRegisterDto {

    private String clientId;
    private String clientSecret;
    private List<AuthorizationGrantType> authorizationGrantTypes;
    private String redirectUris;
    private String postLogoutRedirectUris;
    private List<String> scopes;
    private ClientAuthenticationMethod clientAuthenticationMethods;
}
