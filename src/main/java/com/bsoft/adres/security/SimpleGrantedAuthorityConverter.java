package com.bsoft.adres.security;


import com.bsoft.adres.database.RoleDAO;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Converter
public class SimpleGrantedAuthorityConverter implements
        AttributeConverter<SimpleGrantedAuthority, String> {

    @Override
    public String convertToDatabaseColumn(SimpleGrantedAuthority authority) {
        return Optional.ofNullable(authority)
                .map(SimpleGrantedAuthority::getAuthority)
                .orElse(null);
    }

    @Override
    public SimpleGrantedAuthority convertToEntityAttribute(String role) {
        return Optional.ofNullable(role)
                .map(SimpleGrantedAuthority::new)
                .orElse(null);
    }

    private Collection<? extends GrantedAuthority> getAuthorities(
            Collection<RoleDAO> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (RoleDAO role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRolename()));
            authorities.addAll(role.getPrivileges()
                    .stream()
                    .map(p -> new SimpleGrantedAuthority(p.getName()))
                    .collect(Collectors.toList()));
        }

        return authorities;
    }
}