package com.bsoft.adres.controller;

import com.bsoft.adres.generated.api.LoginApi;
import com.bsoft.adres.generated.model.LoginRequest;
import com.bsoft.adres.generated.model.LoginResponse;
import com.bsoft.adres.generated.model.User;
import com.bsoft.adres.service.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private final UsersService usersService;

    @Autowired
    private final PasswordEncoder passwordEncoder;

    @Value("${info.project.version}")
    private String version;

    @Override
    public ResponseEntity<LoginResponse> _postLogin(String xApiKey, LoginRequest loginRequest) {
        log.debug("_getUser apikey: {}", xApiKey);
        User user = usersService.getUserByName(loginRequest.getUsername());
        String encodedPassword = passwordEncoder.encode(loginRequest.getPassword());

        Boolean authenticated = passwordEncoder.matches(loginRequest.getPassword(), encodedPassword);
        if (encodedPassword.equals(user.getPassword())) {
            authenticated = true;
        }
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setAuthenticated(authenticated);

        return ResponseEntity.status(HttpStatus.OK)
                .header("Version", version)
                .body(loginResponse);
    }

}
