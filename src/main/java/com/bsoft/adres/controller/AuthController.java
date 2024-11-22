package com.bsoft.adres.controller;

import com.bsoft.adres.model.LoginRequest;
import com.bsoft.adres.model.LoginResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthController  {

    @PostMapping("/auth/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest) {
        return LoginResponse
                .builder()
                .accessToken("blablalb")
                .build();
    }
}
