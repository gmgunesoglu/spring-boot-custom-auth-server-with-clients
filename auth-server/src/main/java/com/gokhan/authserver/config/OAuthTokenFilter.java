package com.gokhan.authserver.config;

import com.gokhan.authserver.exceptionhandling.GlobalRuntimeException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.BadJwtException;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidationException;
import org.springframework.security.oauth2.server.authorization.OAuth2Authorization;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@RequiredArgsConstructor
public class OAuthTokenFilter extends OncePerRequestFilter {

    private final OAuth2AuthorizationService tokenService;
    private final JwtDecoder jwtDecoder;


    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwtToken;
        if(authHeader != null && authHeader.startsWith("Bearer ")){
            jwtToken = authHeader.substring(7);
            try{
                Jwt jwt = jwtDecoder.decode(jwtToken);
                String authId = jwt.getClaim("AuthId");
                if (authId != null){
                    OAuth2Authorization oauth = tokenService.findById(authId);
                    System.out.println(oauth);
                    if (oauth != null){
                        Collection<? extends GrantedAuthority> auths = jwt.getClaim("authorities");
                        CustomAuthentication auth = new CustomAuthentication(auths);
                        SecurityContextHolder.getContext().setAuthentication(auth);
                    }
                }
            }catch (JwtValidationException e){
                logger.error(e.getMessage());
            }catch (BadJwtException e){
                logger.error(e.getMessage());
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
        filterChain.doFilter(request,response);
    }
}
