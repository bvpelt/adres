package com.bsoft.adres.service;

import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.repositories.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsersRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            Optional<UserDAO> user = userRepository.findByUsername(username);
            if (user.isEmpty()) {
                throw new UsernameNotFoundException("User: " + username + " not found");
            }
            return new MyUserPrincipal(user.get());
        } catch (Exception e) {
            throw new UsernameNotFoundException("User: " + username + " not found");
        }
    }

}
