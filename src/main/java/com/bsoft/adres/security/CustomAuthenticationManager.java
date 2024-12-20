package com.bsoft.adres.security;

import com.bsoft.adres.repositories.UsersRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

@Slf4j
//@Component
public class CustomAuthenticationManager implements AuthenticationManager {

    //   @Autowired
    private UsersRepository usersRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
/*
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        log.debug("authenticate username: {} password: {}", username, password);

        // Code to make rest call here and check for OK or Unauthorised.
        // What do I return?
        Optional<UserDAO> userDAO = usersRepository.findByUsername(username);
        if (userDAO.isEmpty()) {
            log.debug("user {} not found", username);
            throw new UsernameNotFoundException("User not found");
        }

        UserDAO user = userDAO.get();
        MyUserPrincipal myUserPrincipal = new MyUserPrincipal(user);
        List<GrantedAuthority> authorities = new ArrayList<>();

        user.getRoles().forEach(role -> {

            log.info("get role: {}", role.getRolename());
            role.getPrivileges().forEach(principle -> {
                log.info("get principle: {}", principle.getName());
                authorities.add(new SimpleGrantedAuthority(principle.getName()));
            });
        });

        return new UsernamePasswordAuthenticationToken(username, null, authorities);

 */
        return null;
    }
}
