package com.cpt.payments.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

@Data
@Entity
public class ValidationRule {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String validatorName;

    @Column(nullable = false)
    private boolean isActive;

    @Column(nullable = false)
    private short priority;

    @CreationTimestamp
    private Timestamp creationDate;

    @OneToMany(fetch = FetchType.EAGER)
    private Set<ValidationRuleParam> params;
}
