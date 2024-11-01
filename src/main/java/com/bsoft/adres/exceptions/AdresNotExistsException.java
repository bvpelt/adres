package com.bsoft.adres.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdresNotExistsException extends RuntimeException {

    private String url;

    public AdresNotExistsException() {
        super("Adres already exists");
        this.url = "/";
    }

    public AdresNotExistsException(final String message, final String url) {
        super(message);
        this.url = url;
    }
}
