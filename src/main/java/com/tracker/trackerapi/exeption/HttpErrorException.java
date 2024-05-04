package com.tracker.trackerapi.exeption;

public class HttpErrorException extends RuntimeException {

    private final int statusCode;

    public HttpErrorException(String message, int statusCode) {
        super(message);
        this.statusCode = statusCode;
    }

    public int getStatusCode() {
        return statusCode;
    }
}
