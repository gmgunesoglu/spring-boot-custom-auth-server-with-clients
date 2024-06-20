package com.gokhan.resourceserver.authorization;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
public class CustomAuthentication extends UsernamePasswordAuthenticationToken {

    public CustomAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(null, null, authorities);
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    @Override
    public Object getCredentials() {
        return null;
    }
}
