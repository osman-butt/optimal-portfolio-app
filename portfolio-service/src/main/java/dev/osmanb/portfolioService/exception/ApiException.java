package dev.osmanb.portfolioService.exception;

import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ApiException {
    private final String message;
    private final HttpStatus error;
    private final int statusCode;
    private final ZonedDateTime timeStamp;

    public ApiException(String message, HttpStatus error, ZonedDateTime timeStamp) {
        this.message = message;
        this.statusCode = error.value();
        this.error = error;
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getError() {
        return error;
    }

    public ZonedDateTime getTimeStamp() {
        return timeStamp;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
