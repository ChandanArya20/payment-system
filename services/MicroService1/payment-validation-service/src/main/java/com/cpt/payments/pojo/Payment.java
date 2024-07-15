package com.cpt.payments.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
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
}
