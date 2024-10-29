package com.bsoft.adres.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.Nullable;

public class AdresExistsException extends RuntimeException {

    public AdresExistsException() {
        super("Adres already exists");
    }

    public AdresExistsException(final String message) {
        super(message);
    }
}
