package com.cpt.payments.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PaymentRequest {
    private User user;
    private Payment payment;
}
