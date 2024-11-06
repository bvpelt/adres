package com.bsoft.adres.config;


import com.bsoft.adres.exceptions.AdresExistsException;
import com.bsoft.adres.exceptions.AdresNotExistsException;
import com.bsoft.adres.exceptions.InvalidParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final String adresses = "adresses";

    @ExceptionHandler(AdresExistsException.class)
    public ResponseEntity<ProblemDetail> handleAdresExistsException(AdresExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SEE_OTHER, ex.getMessage());
        problemDetail.setTitle("Adres alreay exists");
        problemDetail.setInstance(URI.create(adresses));

        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(problemDetail);
    }

    @ExceptionHandler(AdresNotExistsException.class)
    public ResponseEntity<ProblemDetail> handleExcehandleAdresNotExistsExceptionption(AdresNotExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Adres not found");
        problemDetail.setInstance(URI.create(adresses));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(InvalidParameterException.class)
    public ResponseEntity<ProblemDetail> handleExcehandleInvalidParameterException(InvalidParameterException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, ex.getMessage());
        problemDetail.setTitle("Invalid parameter");
        problemDetail.setInstance(URI.create(adresses));

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(problemDetail);
    }
}