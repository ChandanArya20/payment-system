package com.cpt.payments.service;

import com.cpt.payments.DTO.ValidationRuleResponse;
import com.cpt.payments.model.ValidationRule;
import com.cpt.payments.model.ValidationRuleParam;
import com.cpt.payments.pojo.ValidationRulePriorityUpdateRequest;
import com.cpt.payments.pojo.ValidationRuleRequest;
import com.cpt.payments.repository.ValidationRuleParamRepository;
import com.cpt.payments.repository.ValidationRuleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ValidationRuleService {

    private ValidationRuleParamRepository validationRuleParamRepo;
    private ValidationRuleRepository validationRuleRepo;
    private ModelMapper modelMapper;

    public Optional<ValidationRule> getValidationRuleById(Integer id) {
        return validationRuleRepo.findById(id);
    }

    public ValidationRule getValidationRuleByValidatorName(String validatorName) {
        return validationRuleRepo.findByValidatorName(validatorName);
    }

    public List<ValidationRule> getAllValidationRules() {
        return (List<ValidationRule>) validationRuleRepo.findAll();
    }

    public ValidationRule saveValidationRule(ValidationRuleRequest validationRule) {
        ValidationRule rule = modelMapper.map(validationRule, ValidationRule.class);
        return validationRuleRepo.save(rule);
    }

    public List<ValidationRuleResponse> getAllValidationRulesWithParams() {
        List<ValidationRule> validationRules = validationRuleRepo.findByIsActiveTrueOrderByPriorityAsc();
        return validationRules.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    private ValidationRuleResponse mapToDto(ValidationRule validationRule) {

        ValidationRuleResponse ruleResponse = modelMapper.map(validationRule, ValidationRuleResponse.class);

        List<ValidationRuleParam> params = validationRuleParamRepo.findByValidatorName(validationRule.getValidatorName());

        Map<String, String> paramsMap = params.stream()
                .collect(Collectors.toMap(ValidationRuleParam::getParamName, ValidationRuleParam::getParamValue));

        ruleResponse.setParams(paramsMap);

        return ruleResponse;
    }

    public ValidationRule deactivateValidationRule(String validatorName) {
        ValidationRule rule = validationRuleRepo.findByValidatorName(validatorName);
        if (rule != null) {
            rule.setActive(false);
            return validationRuleRepo.save(rule);
        } else {
            throw new IllegalArgumentException("Validation rule not found");
        }
    }

    public ValidationRule updateValidationRulePriority(ValidationRulePriorityUpdateRequest updateRequest) {
        ValidationRule rule = validationRuleRepo.findByValidatorName(updateRequest.getValidatorName());
        if (rule != null) {
            rule.setPriority(updateRequest.getPriority());
            return validationRuleRepo.save(rule);
        } else {
            throw new IllegalArgumentException("Validation rule not found");
        }
    }
}
