package com.bsoft.adres.security;

import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.repositories.UsersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MyUserDetailsService implements UserDetailsService {

    private final UsersRepository usersRepository;

    @Override
    public MyUserPrincipal
    loadUserByUsername(String username) throws UsernameNotFoundException {
        log.debug("loadUserByUsername check user: {}", username);
        Optional<UserDAO> userDAO = usersRepository.findByUserName(username);
        if (userDAO.isEmpty()) {
            throw new UsernameNotFoundException("User not found");
        }

        MyUserPrincipal myUserPrincipal = new MyUserPrincipal(userDAO.get());

        return myUserPrincipal;
    }
}
