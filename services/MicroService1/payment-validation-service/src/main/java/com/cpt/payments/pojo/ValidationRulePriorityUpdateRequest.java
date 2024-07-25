package com.cpt.payments.pojo;

import lombok.Data;

@Data
public class ValidationRulePriorityUpdateRequest {
    private String validatorName;
    private short priority;
}
