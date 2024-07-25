package com.cpt.payments.pojo;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class ValidationRuleRequest {

    private String validatorName;
    private Boolean isActive;
    private short priority;
}
