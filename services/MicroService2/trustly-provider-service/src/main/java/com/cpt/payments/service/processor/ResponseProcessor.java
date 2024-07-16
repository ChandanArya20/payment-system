package com.cpt.payments.service.processor;

import com.cpt.payments.constants.ErrorCode;
import com.cpt.payments.dto.PaymentResponseDTO;
import com.cpt.payments.exception.DepositResponseException;
import com.cpt.payments.trustly.res.DepositResponse;
import com.cpt.payments.trustly.res.ResultData;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseProcessor {

    @Autowired
    private Gson gson;

    public PaymentResponseDTO processResponse(ResponseEntity<String> apiResponse) {

        if (apiResponse == null) {
            throw new DepositResponseException(
                    ErrorCode.FAILED_TO_CONNECT_TRUSTLY.getErrorCode(),
                    ErrorCode.FAILED_TO_CONNECT_TRUSTLY.getErrorMessage(),
                    HttpStatus.SERVICE_UNAVAILABLE
            );
        }

        if (apiResponse.getStatusCode() == HttpStatus.OK) {
            DepositResponse depositResponse = gson.fromJson(apiResponse.getBody(), DepositResponse.class);

            System.out.println("Received 2xx success response from trustly: " + depositResponse);

            if (depositResponse != null
                    && depositResponse.getResult() != null
                    && depositResponse.getResult().getData() != null
                    && depositResponse.getResult().getData().getUrl() != null
                    && !depositResponse.getResult().getData().getUrl().trim().isEmpty()) {

                System.out.println("Got successful deposit response from trustly");

                ResultData resultData = depositResponse.getResult().getData();

                PaymentResponseDTO response = PaymentResponseDTO.builder()
                        .redirectUrl(resultData.getUrl())
                        .paymentId(resultData.getOrderid())
                        .build();
                System.out.println("Returning response " + response);
                return response;
            }

        }

        System.out.println("Failed response from trusty");
        throw new DepositResponseException(
                ErrorCode.TRUSTLY_DEPOSIT_ERROR.getErrorCode(),
                ErrorCode.TRUSTLY_DEPOSIT_ERROR.getErrorMessage(),
                HttpStatus.SERVICE_UNAVAILABLE
        );
    }

}
