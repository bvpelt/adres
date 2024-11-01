package com.bsoft.adres.config;


import com.bsoft.adres.exceptions.AdresExistsException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(AdresExistsException.class)
    public ResponseEntity<ProblemDetail> handleException(AdresExistsException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.SEE_OTHER, ex.getMessage());
        problemDetail.setTitle("Adres alreay exists");
        problemDetail.setType(ex.getType());

        return ResponseEntity.status(HttpStatus.SEE_OTHER).body(problemDetail);

    }
}
