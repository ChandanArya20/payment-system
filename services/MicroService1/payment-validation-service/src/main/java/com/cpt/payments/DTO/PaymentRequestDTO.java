package com.cpt.payments.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class PaymentRequestDTO {
    private UserDTO user;
    private PaymentDTO payment;
}
