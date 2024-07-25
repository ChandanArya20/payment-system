package com.cpt.payments.controller;

import com.cpt.payments.DTO.ValidationRuleResponse;
import com.cpt.payments.model.ValidationRule;
import com.cpt.payments.model.ValidationRuleParam;
import com.cpt.payments.pojo.ValidationRuleParamRequest;
import com.cpt.payments.pojo.ValidationRuleParamUpdateRequest;
import com.cpt.payments.pojo.ValidationRulePriorityUpdateRequest;
import com.cpt.payments.pojo.ValidationRuleRequest;
import com.cpt.payments.service.ValidationRuleParamService;
import com.cpt.payments.service.ValidationRuleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/validation-rules")
@AllArgsConstructor
@Slf4j
public class ValidationRuleController {

    private ValidationRuleService validationRuleService;
    private ValidationRuleParamService validationRuleParamService;

    @GetMapping
    public List<ValidationRuleResponse> getAllValidationRulesWithParams() {
        return validationRuleService.getAllValidationRulesWithParams();
    }

    @PostMapping
    public ValidationRule saveValidationRule(@RequestBody ValidationRuleRequest validationRule) {
        return validationRuleService.saveValidationRule(validationRule);
    }

    @PostMapping("/params")
    public ValidationRuleParam saveValidationRuleParam(@RequestBody ValidationRuleParamRequest validationRuleParam) {
        return validationRuleParamService.saveValidationRuleParam(validationRuleParam);
    }

    @PutMapping("/params")
    public ValidationRuleParam updateValidationRuleParam(@RequestBody ValidationRuleParamUpdateRequest updateRequest) {
        return validationRuleParamService.updateValidationRuleParam(updateRequest);
    }

    @PutMapping("/deactivate")
    public ValidationRule deactivateValidationRule(@RequestParam String validatorName) {
        return validationRuleService.deactivateValidationRule(validatorName);
    }

    @PutMapping("/priority")
    public ValidationRule updateValidationRulePriority(@RequestBody ValidationRulePriorityUpdateRequest updateRequest) {
        return validationRuleService.updateValidationRulePriority(updateRequest);
    }

}
