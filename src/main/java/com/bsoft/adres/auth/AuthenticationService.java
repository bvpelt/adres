package com.bsoft.adres.auth;

import com.bsoft.adres.database.RoleDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.exceptions.InvalidUserException;
import com.bsoft.adres.exceptions.UserExistsException;
import com.bsoft.adres.generated.model.*;
import com.bsoft.adres.repositories.RoleRepository;
import com.bsoft.adres.repositories.UsersRepository;
import com.bsoft.adres.security.MyUserPrincipal;
import com.bsoft.adres.service.UsersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final UsersRepository usersRepository;

    private final RoleRepository roleRepository;

    private final UsersService usersService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private final JwtService jwtService;

    private final AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticateResponse register(RegisterRequest request) {
        log.debug("AuthenticationService register - authenticationresponse for request: {}", request.toString());

        Optional<RoleDAO> optionalRoleDAO = roleRepository.findByRolename("USER");
        RoleDAO defRole = null;

        if (optionalRoleDAO.isPresent()) {
            defRole = optionalRoleDAO.get();
        } else {
            RoleDAO roleDAO = new RoleDAO();
            roleDAO.setRolename("JWT-TOKEN");
            roleDAO.setDescription("Using JWT token authentication");
            roleDAO.genHash();
            defRole = roleRepository.save(roleDAO);
        }

        var user = new UserDAO();
        user.setUsername(request.getUsername());
        user.setPassword(bCryptPasswordEncoder.encode(request.getPassword()));
        defRole.addUser(user);
        user.getRoles().add(defRole);
        user.setEmail(request.getEmail());
        user.setPhone(request.getPhone());
        user.genHash();
        try {
            usersRepository.save(user);
        } catch (Exception e) {
            log.error("User: {} not saved", user.getUsername());
            throw new UserExistsException("User already exists based on username or email");
        }

        MyUserPrincipal myUserPrincipal = new MyUserPrincipal(user);
        var jwtToken = jwtService.generateToken(myUserPrincipal);

        log.trace("AuthenticationService register - generated token: {}", jwtToken);

        return new AuthenticateResponse(jwtToken);
    }

    public AuthenticateResponse authenticate(AuthenticateRequest request) {

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            log.error("Authentication failed for user: {}", request.getUsername());
            throw new InvalidUserException("Authentication failed for user: " + request.getUsername());
        }

        var user = usersRepository
                .findByUserName(request.getUsername())
                .orElseThrow();

        MyUserPrincipal myUserPrincipal = new MyUserPrincipal(user);
        var jwtToken = jwtService.generateToken(myUserPrincipal);

        log.trace("AuthenticationService authenticate - generated token: {}", jwtToken);

        return new AuthenticateResponse(jwtToken);
    }

    public LoginTestResponse basicjwt(LoginRequest loginRequest) {
        log.debug("basicjwt loginRequest: {}", loginRequest.toString());

        LoginTestResponse loginTestResponse = new LoginTestResponse();

        User user = usersService.getUserByName(loginRequest.getUsername());

        Boolean authenticated = bCryptPasswordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (authenticated) {
            MyUserPrincipal myUserPrincipal = new MyUserPrincipal(new UserDAO(user));
            var jwtToken = jwtService.generateToken(myUserPrincipal);

            loginTestResponse.setToken(jwtToken);
        }

        loginTestResponse.setAuthenticated(authenticated);

        return loginTestResponse;
    }
}
