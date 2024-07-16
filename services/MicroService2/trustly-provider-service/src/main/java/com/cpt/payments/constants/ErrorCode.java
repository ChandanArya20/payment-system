package com.cpt.payments.constants;

import lombok.Getter;

@Getter
public enum ErrorCode {

	GENERIC_ERROR(20000, "something went wrong, please try again later."),
	FAILED_TO_CONNECT_TRUSTLY(20001, "Unable to connect with trustly system"),
	TRUSTLY_DEPOSIT_ERROR(20002, "trustly exception");


	private final int errorCode;
    private final String errorMessage;
    
    ErrorCode(int errorCode, String errorMessage) {
    	this.errorCode = errorCode;
    	this.errorMessage = errorMessage;
    }

}
