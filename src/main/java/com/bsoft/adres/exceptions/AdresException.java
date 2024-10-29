package com.bsoft.adres.exceptions;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.lang.Nullable;

public class AdresException extends RuntimeException {

    private final HttpHeaders headers = null;
    private final ProblemDetail body = null;
    private final String messageDetailCode = null;
    @Nullable
    private final Object[] messageDetailArguments = null;
    private HttpStatusCode status = HttpStatus.OK;

    public AdresException(final HttpStatusCode status, final Throwable cause) {
        super(cause);
        this.status = status;
    }

    public AdresException() {
        super();
    }

    public AdresException(HttpStatusCode status) {
        this(status, null);
    }

    public AdresException(String message) {
        super(message);
    }

    public AdresException(String message, Throwable cause) {
        super(message, cause);
    }

    protected AdresException(String message, Throwable cause,
                             boolean enableSuppression,
                             boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
