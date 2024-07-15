package com.cpt.payments.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.http.HttpServiceEngine;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.processor.RequestProcessor;
import com.cpt.payments.service.processor.ResponseProcessor;

@Service
public class PaymentServiceImpl implements PaymentService {

	@Autowired
	private RequestProcessor requestProcessor;
	
	@Autowired
	private HttpServiceEngine httpServiceEngine;
	
	@Autowired
	private ResponseProcessor responseProcessor;
	
	
	/**
	 * Prepare Request
	 * Make API Call
	 * Process response
	 */
	@Override
	public PaymentResponseDTO initiatePayment(PaymentRequestDTO reqDTO) {
		
		HttpRequest httpRequest = requestProcessor.prepareRequest(reqDTO);
		
		ResponseEntity<String> apiResponse = httpServiceEngine.execute(httpRequest);
		// Process the response
		PaymentResponseDTO response = responseProcessor.processResponse(apiResponse);
		
		System.out.println("Returning response:" + response);
		
		return response;
	}

}
