package com.cpt.payments.service;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.DTO.PaymentResponseDTO;

public interface PaymentService {

    PaymentResponseDTO validateAndInitiate(PaymentRequestDTO request);

}
