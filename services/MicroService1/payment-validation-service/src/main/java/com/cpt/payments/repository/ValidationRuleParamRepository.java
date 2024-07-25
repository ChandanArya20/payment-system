package com.cpt.payments.repository;

import com.cpt.payments.model.ValidationRuleParam;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ValidationRuleParamRepository extends CrudRepository<ValidationRuleParam, Integer> {
    List<ValidationRuleParam> findByValidatorName(String validatorName);
    Optional<ValidationRuleParam> findByValidatorNameAndParamName(String validatorName, String paramName);
}
