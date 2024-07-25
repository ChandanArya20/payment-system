package com.cpt.payments.DTO;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ValidationRuleResponse {

    private Integer id;
    private String validatorName;
    private boolean isActive;
    private short priority;
    private LocalDateTime creationDate;
    private Map<String, String> params;

}
