package com.cpt.payments.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.sql.Timestamp;

@Setter
@Getter
@ToString
public class MerchantPaymentRequest {
    private int id;
    private String merchantTransactionReference;
    private String transactionRequest;
    private Timestamp creationDate;
}
