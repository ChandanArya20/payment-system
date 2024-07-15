package com.cpt.payments.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final HttpStatus status;

    public ValidationException(int errorCode, String errorMessage, HttpStatus status) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public HttpStatus getStatus() {
        return status;
    }
}

