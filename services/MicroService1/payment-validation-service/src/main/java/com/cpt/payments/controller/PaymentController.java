package com.cpt.payments.controller;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.DTO.PaymentResponseDTO;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.PaymentResponse;
import com.cpt.payments.service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/payments")
public class PaymentController {

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PaymentService service;

    @PostMapping
    public ResponseEntity<PaymentResponse> initPayment(@RequestBody PaymentRequest request) throws JsonProcessingException {
        System.out.println("PaymentController.initPayment() invoked || request: " + request);

        PaymentRequestDTO reqDTO = modelMapper.map(request, PaymentRequestDTO.class);
        PaymentResponseDTO responseDTO = service.validateAndInitiate(reqDTO);
        PaymentResponse response = modelMapper.map(responseDTO, PaymentResponse.class);

        System.out.println("Returning response: " + response);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}
