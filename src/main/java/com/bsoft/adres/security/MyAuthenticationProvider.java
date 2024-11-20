package com.bsoft.adres.security;

import com.bsoft.adres.database.PrivilegeDAO;
import com.bsoft.adres.database.RoleDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.exceptions.UserNotExistsException;
import com.bsoft.adres.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UsersRepository usersRepository;

    public MyAuthenticationProvider (UsersRepository usersRepository) {
        this.usersRepository =usersRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        final String name = authentication.getName();
        final String password = authentication.getCredentials().toString();
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();

        Optional<UserDAO> user = usersRepository.findByUsername(name);
        if (user.isEmpty()) {
            throw new UserNotExistsException("User " + name + " not found");
        }
        MyUserPrincipal principal = new MyUserPrincipal(user.get());
        Collection<RoleDAO> roles = user.get().getRoles();
        roles.forEach(role -> {
            log.debug("Check role: {} on privileges", role.getRolename());
            Collection<PrivilegeDAO> privileges = role.getPrivileges();
            privileges.forEach(privilege -> {
                log.debug("Check privileg: {}", privilege.getName());
                grantedAuths.add(new SimpleGrantedAuthority(privilege.getName()));
            });
        });

        return new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }

/*
    private UsernamePasswordAuthenticationToken authenticateAgainstThirdPartyAndGetAuthentication(String name, String password) {
        final List<GrantedAuthority> grantedAuths = new ArrayList<>();
        /*
        grantedAuths.add(new SimpleGrantedAuthority("ROLE_USER"));
        final UserDetails principal = new User(name, password, grantedAuths);


         *  /

        Optional<UserDAO> user = usersRepository.findByUsername(name);
        if (user.isEmpty()) {
            throw new UserNotExistsException("User " + name + " not found");
        }
        MyUserPrincipal principal = new MyUserPrincipal(user.get());
        Collection<RoleDAO> roles = user.get().getRoles();
        roles.forEach(role -> {
            Collection<PrivilegeDAO> privileges = role.getPrivileges();
            privileges.forEach(privilege -> {
                grantedAuths.add(new SimpleGrantedAuthority(privilege.getName()));
            });
        });

        return new UsernamePasswordAuthenticationToken(principal, password, grantedAuths);
    }
    */
}
