package com.bsoft.adres.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class AdresNotDeletedException extends RuntimeException {
    public AdresNotDeletedException(final String message) {
        super(message);
    }
}
