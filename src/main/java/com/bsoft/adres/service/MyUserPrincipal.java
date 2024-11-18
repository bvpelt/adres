package com.bsoft.adres.service;

import com.bsoft.adres.database.UserDAO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
public class MyUserPrincipal implements UserDetails {

    private final UserDAO user;

    public MyUserPrincipal(UserDAO user) {
        this.user = user;
        log.info("MyUserPrincipal created {}", user.getUsername());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {
            //authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
            log.info("Add role: {}", role.getRolename());
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getAccount_non_expired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getAccount_non_locked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return user.getCredentials_non_expired();
    }

    @Override
    public boolean isEnabled() {
        return user.getEnabled();
    }
}
