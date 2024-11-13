package com.bsoft.adres.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class UserExistsException extends RuntimeException {
    public UserExistsException(final String message) {
        super(message);
    }
}
