package dev.osmanb.portfolioService.exception;

public class TooManyRequests extends RuntimeException{
    public TooManyRequests() {
    }
    public TooManyRequests(String message) {
        super(message);
    }

    public TooManyRequests(Throwable cause) {
        super(cause);
    }

    public TooManyRequests(String message, Throwable cause) {
        super(message, cause);
    }

}
