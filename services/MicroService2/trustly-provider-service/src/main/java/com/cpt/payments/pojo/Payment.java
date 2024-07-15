package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class Payment {
    private String merchantTxnRef;
    private String currency;
    private String country;
    private String locale;
    private String shopperStatement;
    private String successURL;
    private String failureUrl;
    private String amount;
    private String paymentMethod;
    private String paymentType;
    private String providerId;

    private String notificationUrl;
    private String transactionReference;
}
