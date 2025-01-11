package com.bsoft.adres.exceptions;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
public class PrivilegeNotDeletedException extends RuntimeException {
    public PrivilegeNotDeletedException(final String message) {
        super(message);
    }
}
