package com.cpt.payments.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public class DepositResponseException extends RuntimeException {
    private final int errorCode;
    private final String errorMessage;
    private final HttpStatusCode status;

    public DepositResponseException(int errorCode, String errorMessage, HttpStatusCode status) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.status = status;
    }

}

