package com.bsoft.adres.model;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class LoginResponse {
    private final String accessToken;
}
