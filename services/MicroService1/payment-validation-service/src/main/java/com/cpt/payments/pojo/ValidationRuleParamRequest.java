package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class ValidationRuleParamRequest {
    private String validatorName;
    private String paramName;
    private String paramValue;
}
