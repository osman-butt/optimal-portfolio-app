package dev.osmanb.portfolioService.exception;

public class UnprocessableContent extends RuntimeException{
    public UnprocessableContent() {
    }

    public UnprocessableContent(String message) {
        super(message);
    }

    public UnprocessableContent(Throwable cause) {
        super(cause);
    }

    public UnprocessableContent(String message, Throwable cause) {
        super(message, cause);
    }

}
