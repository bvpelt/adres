package com.bsoft.adres.exceptions;

public class AdresExistsException extends RuntimeException {

    public AdresExistsException() {
        super("Adres already exists");
    }

    public AdresExistsException(final String message) {
        super(message);
    }
}
