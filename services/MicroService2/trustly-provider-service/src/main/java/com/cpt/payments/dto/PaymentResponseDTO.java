package com.cpt.payments.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaymentResponseDTO {
	
	private String paymentId;
	private String redirectUrl;

}
