package com.cpt.payments.service.impl;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.DTO.PaymentResponseDTO;
import com.cpt.payments.constant.ValidationRules;
import com.cpt.payments.dao.MerchantPaymentDao;
import com.cpt.payments.service.PaymentService;
import com.cpt.payments.service.Validator;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService {

    @Value("${payment.validators}")
    private String validatorRules;

    @Autowired
    private ApplicationContext appContext;

    @Override
    public PaymentResponseDTO validateAndInitiate(PaymentRequestDTO request) {
        System.out.println("PaymentServiceImpl.validateAndInitiate() received request: " + request);

        String[] validatorRulesArray = validatorRules.split(",");

        for (String ruleName : validatorRulesArray) {
            ValidationRules rule = ValidationRules.fromName(ruleName);

            // returns the spring bean validation rule Class.
            Validator validationBean = (Validator) appContext.getBean(rule.getClazz());
            System.out.println("Got this bean from AppContext validationBean:" + validationBean);

            validationBean.doValidate(request);
        }

        PaymentResponseDTO response = new PaymentResponseDTO();
        response.setRedirectURL("https://www.google.com/");

        return response;
    }
}
