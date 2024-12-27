package com.bsoft.adres.controller;

import com.bsoft.adres.auth.AuthenticationService;
import com.bsoft.adres.generated.api.LoginApi;
import com.bsoft.adres.generated.model.*;
import com.bsoft.adres.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/adres/api/v1")
@Controller
public class LoginController implements LoginApi {

    private final UsersService usersService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationService authenticationService;

    @Value("${info.project.version}")
    private String version;

    @PostMapping("/login/user")
    @Override
    public ResponseEntity<LoginResponse> _postLogin(LoginRequest loginRequest, String X_API_KEY) {
        log.debug("_getUser apikey: {}", X_API_KEY);
        User user = usersService.getUserByName(loginRequest.getUsername());
        String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());

        Boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());
        if (encodedPassword.equals(user.getPassword())) {
            authenticated = true;
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAuthenticated(authenticated);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(loginResponse);
    }

    @PostMapping("/login/jwt/authenticate")
    @Override
    public ResponseEntity<AuthenticateResponse> _postAuthenticate(String X_API_KEY, AuthenticateRequest authenticateRequest) {
        log.debug("_postAuthenticate apikey: {}", X_API_KEY);
        return ResponseEntity.ok(authenticationService.authenticate(authenticateRequest));
    }

    @PostMapping("/login/jwt/register")
    @Override
    public ResponseEntity<AuthenticateResponse> _postRegister(String X_API_KEY, RegisterRequest registerRequest) {
        log.debug("_postRegister apikey: {}", X_API_KEY);
        return ResponseEntity.ok(authenticationService.register(registerRequest));
    }

}
