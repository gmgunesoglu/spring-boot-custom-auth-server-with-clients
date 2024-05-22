package com.gokhan.authserver.authentication;

import com.gokhan.authserver.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;
import java.util.List;

@Getter
@Setter
public class CustomAuthentication implements Authentication {

    private final boolean authenticated;
    private Collection<? extends GrantedAuthority> authorities;


    public CustomAuthentication(){
        authenticated = false;
    }

    public CustomAuthentication(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
        authenticated = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getDetails() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

    // oluşturulmadan önce zaten kontrol edeceğim
    // oluşturulmuş tüm authentication lar doğrulanmışolacak.
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

    }

    @Override
    public String getName() {
        return "";
    }
}
