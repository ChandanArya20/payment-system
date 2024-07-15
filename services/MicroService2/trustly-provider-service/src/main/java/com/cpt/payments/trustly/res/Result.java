package com.cpt.payments.trustly.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class Result {
    private String signature;
    private String uuid;
    private String method;
    private ResultData data;

}
