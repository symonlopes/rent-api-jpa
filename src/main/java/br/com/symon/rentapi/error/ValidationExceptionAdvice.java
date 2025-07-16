package br.com.symon.rentapi.error;


import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.sql.SQLSyntaxErrorException;

/**
 * Converts validation exceptions into a structured error response, frontend friendly.
 */
@ControllerAdvice
@Slf4j
public class ValidationExceptionAdvice {

    @ExceptionHandler(SQLSyntaxErrorException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> sQLSyntaxErrorException(SQLSyntaxErrorException ex) {
        log.warn("Error on query: {} ", ex.getMessage());
        var response = ErrorResponse.builder().build();
        response.getErrors().add(
                ApiError.builder()
                        .message("An error occurred while processing the request.")
                        .build());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        var response = ErrorResponse.builder().build();

        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            response.getErrors().add(
                    ApiError.builder()
                            //code here
                            .message(error.getDefaultMessage())
                            .build());
        }

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}