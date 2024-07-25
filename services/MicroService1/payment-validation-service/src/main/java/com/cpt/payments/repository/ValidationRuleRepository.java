package com.cpt.payments.repository;

import com.cpt.payments.model.ValidationRule;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ValidationRuleRepository extends CrudRepository<ValidationRule, Integer> {
    ValidationRule findByValidatorName(String validatorName);
    List<ValidationRule> findByIsActiveTrueOrderByPriorityAsc();
}
