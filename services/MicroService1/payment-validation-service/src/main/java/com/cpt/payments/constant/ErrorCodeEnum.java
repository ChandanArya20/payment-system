package com.cpt.payments.constant;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {

	GENERIC_ERROR(10000, "Unable to process your request, please try again later."),
	REQUEST_STRUCTURE_INVALID(10001, "Invalid Request, please provide 'user' and 'payment' in request"),
	SIGNATURE_GENERATION_ERROR(10002, "Failure generating signature"),
	SIGNATURE_INVALID_ERROR(10003, "Invalid signature, can't process"),
	DUPLICATE_TXN_ERROR(10004, "Invalid request, duplicate 'merchantTxnReference', please provide unique value"),
	MERCHANT_TXN_REF_EMPTY(10005, "merchantTxnRef received as null or empty"),
	END_USER_ID_EMPTY(10006, "endUserId is null or empty"),
	PAYMENT_ATTEMPT_LIMIT_EXCEEDED(10007, "User attempt limit exceeded");

	private final int errorCode;
    private final String errorMessage;
    
    ErrorCodeEnum(int errorCode, String errorMessage) {
    	this.errorCode = errorCode;
    	this.errorMessage = errorMessage;
    }

}
