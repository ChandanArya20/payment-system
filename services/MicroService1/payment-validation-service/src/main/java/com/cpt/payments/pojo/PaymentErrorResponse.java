package com.cpt.payments.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PaymentErrorResponse {

	private int errorCode;
	private String errorMessage;

	@Override
	public String toString() {
		return "PaymentErrorResponse [errorCode=" + errorCode + ", errorMessage=" + errorMessage + "]";
	}
}
