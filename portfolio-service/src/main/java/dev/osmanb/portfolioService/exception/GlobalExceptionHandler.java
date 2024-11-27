package dev.osmanb.portfolioService.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.ZonedDateTime;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnprocessableContent.class)
    public ResponseEntity<Object> handleUncprocessableContent(UnprocessableContent ex) {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY, ZonedDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(TooManyRequests.class)
    public ResponseEntity<Object> handleTooManyRequests(TooManyRequests ex) {
        ApiException apiException = new ApiException(ex.getMessage(), HttpStatus.TOO_MANY_REQUESTS,ZonedDateTime.now());
        return new ResponseEntity<>(apiException, HttpStatus.TOO_MANY_REQUESTS);
    }

}
