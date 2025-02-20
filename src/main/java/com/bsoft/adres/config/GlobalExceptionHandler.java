package com.bsoft.adres.config;

import com.bsoft.adres.exceptions.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.URI;
import java.util.Map;

@ControllerAdvice
@Slf4j

public class GlobalExceptionHandler {

    private static final Map<Class<? extends Exception>, ExceptionInfo> EXCEPTION_MAPPING = Map.of(
            AdresExistsException.class, new ExceptionInfo(HttpStatus.SEE_OTHER, "Adres already exists", "adresses"),
            AdresNotDeletedException.class, new ExceptionInfo(HttpStatus.NOT_FOUND, "Adres not deleted", "adresses"),
            AdresNotExistsException.class, new ExceptionInfo(HttpStatus.NOT_FOUND, "Adres not found", "adresses"),
            InvalidParameterException.class, new ExceptionInfo(HttpStatus.BAD_REQUEST, "Invalid parameter", "adresses"),
            PersonExistsException.class, new ExceptionInfo(HttpStatus.NOT_FOUND, "Person already exists", "persons"),
            PersonNotExistsException.class, new ExceptionInfo(HttpStatus.NOT_FOUND, "Person not found", "persons"),
            UserNotExistsException.class, new ExceptionInfo(HttpStatus.NOT_FOUND, "User problem", "users")
    );

    @ExceptionHandler(Exception.class) // Catch-all for other exceptions if needed
    public ResponseEntity<ProblemDetail> handleException(Exception ex) {
        log.error("Unhandled exception: ", ex); // Log the actual exception details
        return createProblemDetail(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error", "error"); // Generic error response
    }

    @ExceptionHandler({
            AdresExistsException.class, AdresNotDeletedException.class, AdresNotExistsException.class,
            InvalidParameterException.class,
            PersonExistsException.class, PersonNotExistsException.class,
            UserNotExistsException.class
    })
    public ResponseEntity<ProblemDetail> handleCustomExceptions(Exception ex) {
        ExceptionInfo exceptionInfo = EXCEPTION_MAPPING.get(ex.getClass());
        if (exceptionInfo == null) {
            log.warn("No specific mapping found for exception: {}", ex.getClass().getSimpleName());
            return handleException(ex); // Fallback to generic handler or handle appropriately
        }
        return createProblemDetail(exceptionInfo.status, exceptionInfo.title, exceptionInfo.instance);
    }

    private ResponseEntity<ProblemDetail> createProblemDetail(HttpStatus status, String title, String instance) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(status, title);
        problemDetail.setInstance(URI.create(instance));
        return ResponseEntity.status(status).body(problemDetail);
    }

    private record ExceptionInfo(HttpStatus status, String title, String instance) {}
}
/*
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
 */
