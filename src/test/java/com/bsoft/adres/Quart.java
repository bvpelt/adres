package com.bsoft.adres;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Getter
@Setter
public class Quart {
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    private String username;
    private String password;
    private String email;
    private String phone;
    private Boolean account_non_expired = true;
    private Boolean account_non_locked = true;
    private Boolean credentials_non_expired = true;
    private Boolean enabled = true;


    public Quart(String username, String password, String email, String phone) {
        this.username = username;
        this.password = bCryptPasswordEncoder.encode(password);
        this.email = email;
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Quart{" +
                "bCryptPasswordEncoder=" + bCryptPasswordEncoder +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", account_non_expired=" + account_non_expired +
                ", account_non_locked=" + account_non_locked +
                ", credentials_non_expired=" + credentials_non_expired +
                ", enabled=" + enabled +
                '}';
    }
}