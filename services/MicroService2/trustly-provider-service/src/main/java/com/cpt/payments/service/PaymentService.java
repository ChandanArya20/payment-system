package com.cpt.payments.service;

import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.dto.PaymentResponseDTO;

public interface PaymentService {

	PaymentResponseDTO initiatePayment(PaymentRequestDTO reqDTO);

}
