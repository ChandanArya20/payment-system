package com.cpt.payments.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class ValidationRuleParam {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false)
    private String validatorName;

    @Column(nullable = false)
    private String paramName;

    @Column(nullable = false)
    private String paramValue;

    @CreationTimestamp
    private Timestamp creationDate;

}
