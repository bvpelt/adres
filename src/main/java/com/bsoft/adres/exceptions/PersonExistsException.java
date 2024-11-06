package com.bsoft.adres.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class PersonExistsException extends RuntimeException {

    private final String host = "http://localhost:8080";

    public PersonExistsException(final String message) {
        super(message);
    }
}
