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

    @Override
    public ResponseEntity<LoginResponse> _postLogin(LoginRequest loginRequest, String X_API_KEY) {
        log.debug("_getUser apikey: {}", X_API_KEY);
        User user = usersService.getUserByName(loginRequest.getUsername());

        Boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), user.getPassword());

        LoginResponse loginResponse = new LoginResponse(authenticated);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(loginResponse);
    }

    @Override
    public ResponseEntity<AuthenticateResponse> _postAuthenticate(String X_API_KEY, AuthenticateRequest authenticateRequest) {
        log.debug("_postAuthenticate apikey: {}", X_API_KEY);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(authenticationService.authenticate(authenticateRequest));
    }

    @Override
    public ResponseEntity<AuthenticateResponse> _postRegister(String X_API_KEY, RegisterRequest registerRequest) {
        log.debug("_postRegister apikey: {}", X_API_KEY);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(authenticationService.register(registerRequest));
    }

    @Override
    public ResponseEntity<LoginTestResponse> _postTestLogin(LoginRequest loginRequest, String X_API_KEY) {
        log.debug("_postTestLogin apikey: {}", X_API_KEY);
        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(authenticationService.basicjwt(loginRequest));
    }

}
