package com.cpt.payments.trustly.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class DepositResponse {
    private Result result;
    private String version;

}
