package com.cpt.payments.trustly.res;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class ResultData {
    private String orderid;
    private String url;

}