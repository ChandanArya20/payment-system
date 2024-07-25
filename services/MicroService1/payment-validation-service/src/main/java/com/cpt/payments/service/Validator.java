package com.cpt.payments.service;

import com.cpt.payments.DTO.PaymentRequestDTO;

public interface Validator {

    void doValidate(PaymentRequestDTO request);

}
