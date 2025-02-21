package com.bsoft.adres.controller;

import com.bsoft.adres.generated.api.LoginApi;
import com.bsoft.adres.generated.model.LoginRequest;
import com.bsoft.adres.generated.model.LoginResponse;
import com.bsoft.adres.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("${application.basePath}")
@Controller
public class LoginController implements LoginApi {

    private final AuthenticationService authenticationService;

    @Value("${info.project.version}")
    private String version;

    private HttpHeaders getVersionHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Version", version);
        return headers;
    }

    @Override
    public ResponseEntity<LoginResponse> _postLogin(String X_API_KEY, LoginRequest loginRequest) {
        log.debug("_postTestLogin apikey: {}", X_API_KEY);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(getVersionHeaders())
                .body(authenticationService.basicjwt(loginRequest));
    }

    @Override
    public ResponseEntity<LoginResponse> _postAuthenticate(String X_API_KEY, LoginRequest loginRequest) {
        log.debug("_postAuthenticate apikey: {}", X_API_KEY);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(getVersionHeaders())
                .body(authenticationService.authenticate(loginRequest));
    }

    @Override
    public ResponseEntity<LoginResponse> _postRegister(String X_API_KEY, LoginRequest loginRequest) {
        log.debug("_postRegister apikey: {}", X_API_KEY);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(getVersionHeaders())
                .body(authenticationService.register(loginRequest));
    }

}
