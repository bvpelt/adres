package com.bsoft.adres.auth;

import com.bsoft.adres.database.RolesDAO;
import com.bsoft.adres.database.UserDAO;
import com.bsoft.adres.exceptions.InvalidUserException;
import com.bsoft.adres.exceptions.UserExistsException;
import com.bsoft.adres.generated.model.*;
import com.bsoft.adres.jwt.JwtUtils;
import com.bsoft.adres.repositories.RoleRepository;
import com.bsoft.adres.repositories.UsersRepository;
import com.bsoft.adres.security.MyUserPrincipal;
import com.bsoft.adres.service.UsersService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service

public class AuthenticationService {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private UsersService usersService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /*
    @Autowired
    private JwtService jwtService;
*/

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Transactional
    public AuthenticateResponse register(RegisterRequest request) {
        log.debug("AuthenticationService register - authenticationresponse for request: {}", request.toString());

        Optional<RolesDAO> optionalRoleDAO = roleRepository.findByRolename("USER");
        RolesDAO defRole = null;

        if (optionalRoleDAO.isPresent()) {
            defRole = optionalRoleDAO.get();
        } else {
            RolesDAO rolesDAO = new RolesDAO();
            rolesDAO.setRolename("JWT-TOKEN");
            rolesDAO.setDescription("Using JWT token authentication");
            rolesDAO.genHash();
            defRole = roleRepository.save(rolesDAO);
        }

        var user = new UserDAO();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
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
        //var jwtToken = jwtService.generateToken(myUserPrincipal);
        var jwtToken = jwtUtils.generateTokenFromUsername(myUserPrincipal);

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
        //var jwtToken = jwtService.generateToken(myUserPrincipal);
        var jwtToken = jwtUtils.generateTokenFromUsername(myUserPrincipal);

        log.trace("AuthenticationService authenticate - generated token: {}", jwtToken);

        return new AuthenticateResponse(jwtToken);
    }

    public LoginResponse basicjwt(LoginRequest loginRequest) {
        log.debug("basicjwt loginRequest: {}", loginRequest.toString());

        LoginResponse loginResponse = new LoginResponse();

        User user = usersService.getUserByName(loginRequest.getUsername());

        Boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (authenticated) {
            MyUserPrincipal myUserPrincipal = new MyUserPrincipal(new UserDAO(user));
            //var jwtToken = jwtService.generateToken(myUserPrincipal);
            var jwtToken = jwtUtils.generateTokenFromUsername(myUserPrincipal);

            loginResponse.setToken(jwtToken);
        }

        loginResponse.setAuthenticated(authenticated);

        return loginResponse;
    }
}
