package com.gokhan.authserver.dto.client;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;

import java.util.List;

@Getter
@Builder
public class ClientRegisterDto {

    private String realmName;
    private String name;            // clientId
    private String clientSecret;
    private String baseUrl;
    private List<AuthorizationGrantType> authorizationGrantTypes;
    private String redirectUri;
    private String postLogoutRedirectUri;
    private ClientAuthenticationMethod clientAuthenticationMethod;
    private String accessTokenDuration;
    private String refreshTokenDuration;
    private String authorizationCodeDuration;
}
