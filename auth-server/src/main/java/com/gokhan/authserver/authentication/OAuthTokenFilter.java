package com.gokhan.authserver.authentication;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class OAuthTokenFilter extends OncePerRequestFilter {

    private final OAuth2AuthorizationService oauthService;
    private final JwtDecoder jwtDecoder;

    public OAuthTokenFilter(OAuth2AuthorizationService oauthService, JwtDecoder jwtDecoder) {
        this.oauthService = oauthService;
        this.jwtDecoder = jwtDecoder;
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String accessToken;
        final String userName;
        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        accessToken = authHeader.substring(7);
        Jwt jwt = jwtDecoder.decode(accessToken);
        Map<String, Object> claims = jwt.getClaims();
        String tokenId = (String) claims.get("tokenId");
        List<String> authorities = (List<String>) claims.get("authorities");
        OAuth2Authorization oAuth2Authorization = oauthService.findById(tokenId);
        if(oAuth2Authorization != null){
            Authentication authentication = new CustomAuthentication(authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        filterChain.doFilter(request,response);
    }

}
