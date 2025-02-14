package com.bsoft.adres.config;

import com.bsoft.adres.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;

//@RestControllerAdvice
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private final String adresses = "adresses";
    private final String persons = "persons";
    private final String users = "users";

    @ExceptionHandler(AdresExistsException.class)
    public ResponseEntity<ProblemDetail> handleAdresExistsException(AdresExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SEE_OTHER, ex.getMessage());
        problemDetail.setTitle("Adres already exists");
        problemDetail.setInstance(URI.create(adresses));

        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(problemDetail);
    }

    @ExceptionHandler(AdresNotDeletedException.class)
    public ResponseEntity<ProblemDetail> handleExcehandleAdresNotDeletedException(AdresNotDeletedException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Adres not deleted");
        problemDetail.setInstance(URI.create(adresses));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
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

    @ExceptionHandler(PersonExistsException.class)
    public ResponseEntity<ProblemDetail> handleExcehandlePersonExistsExceptionption(PersonExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Person already exists");
        problemDetail.setInstance(URI.create(persons));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(PersonNotExistsException.class)
    public ResponseEntity<ProblemDetail> handleExcehandlePersonNotExistsExceptionption(PersonNotExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("Person not found");
        problemDetail.setInstance(URI.create(persons));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

    @ExceptionHandler(UserNotExistsException.class)
    public ResponseEntity<ProblemDetail> handleExcehandleUserNotExistsException(UserNotExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problemDetail.setTitle("User problem");
        problemDetail.setInstance(URI.create(users));

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(problemDetail);
    }

}
