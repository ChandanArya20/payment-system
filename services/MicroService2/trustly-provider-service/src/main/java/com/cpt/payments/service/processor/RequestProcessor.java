package com.cpt.payments.service.processor;

import com.cpt.payments.dto.PaymentDTO;
import com.cpt.payments.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

import com.cpt.payments.constants.Constants;
import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.trustly.req.Attributes;
import com.cpt.payments.trustly.req.Data;
import com.cpt.payments.trustly.req.DepositRequest;
import com.cpt.payments.trustly.req.Params;
import com.google.gson.Gson;

@Component
public class RequestProcessor {
	
	@Autowired
	private Gson gson;

	public HttpRequest prepareRequest(PaymentRequestDTO request) {

		PaymentDTO payment = request.getPayment();
		UserDTO user = request.getUser();

		Attributes attributesRef = Attributes.builder()
				.country(payment.getCountry())
				.locale(payment.getLocale())
			    .currency(payment.getCurrency())
			    .amount(payment.getAmount())
			    .mobilePhone(user.getPhone())
			    .firstname(user.getFirstName())
			    .lastname(user.getLastName())
			    .email(user.getEmail())
			    .successURL(payment.getSuccessURL())
			    .failURL(payment.getFailureUrl())
			    .build();
				
		
		Data data = Data.builder()
				.username("UNAME")//TODO store & access from AWS Secret Manager
			    .password("PWD")//TODO store & access from AWS Secret Manager
			    .notificationURL(payment.getNotificationUrl())
			    .endUserID(user.getEndUserId())
			    .messageID(payment.getTransactionReference())
			    .attributes(attributesRef)
				.build();
		
		//TODO Generate RSA Signature & pass to Trustly
		Params params = Params.builder()
				.signature("")
				.uuid(payment.getTransactionReference())
				.data(data)
				.build();
		
		
		DepositRequest depositReq = DepositRequest.builder()
			    .method(Constants.TRUSTLY_DEPOSIT)
			    .params(params)
			    .version(Constants.TRUSTLY_API_VERSION)
				.build();
		
		
		System.out.println("depositReq:" + depositReq);
		
		String jsonString = gson.toJson(depositReq);
		
		System.out.println("jsonString:" + jsonString);
		
		HttpRequest httpRequest = HttpRequest.builder()
				.url("http://localhost:8084/payment/initiate")
				.requestBody(jsonString)
				.method(HttpMethod.POST)
				.build();
		
		System.out.println("httpRequest:" + httpRequest);
		
		return httpRequest;
	}
}
