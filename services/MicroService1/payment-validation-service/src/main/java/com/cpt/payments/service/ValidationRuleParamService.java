package com.cpt.payments.service;

import com.cpt.payments.model.ValidationRule;
import com.cpt.payments.model.ValidationRuleParam;
import com.cpt.payments.pojo.ValidationRuleParamRequest;
import com.cpt.payments.pojo.ValidationRuleParamUpdateRequest;
import com.cpt.payments.repository.ValidationRuleParamRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class ValidationRuleParamService {

    private ValidationRuleParamRepository validationRuleParamRepo;
    private ModelMapper modelMapper;

    public Optional<ValidationRuleParam> getValidationRuleParamById(Integer id) {
        return validationRuleParamRepo.findById(id);
    }

    public List<ValidationRuleParam> getValidationRuleParamsByValidatorName(String validatorName) {
        return validationRuleParamRepo.findByValidatorName(validatorName);
    }

    public ValidationRuleParam saveValidationRuleParam(ValidationRuleParamRequest validationRuleParam) {
        ValidationRuleParam ruleParam = modelMapper.map(validationRuleParam, ValidationRuleParam.class);
        return validationRuleParamRepo.save(ruleParam);
    }

    public ValidationRuleParam updateValidationRuleParam(ValidationRuleParamUpdateRequest updateRequest) {
        Optional<ValidationRuleParam> existingParam = validationRuleParamRepo
                .findByValidatorNameAndParamName(updateRequest.getValidatorName(), updateRequest.getParamName());

        if (existingParam.isPresent()) {
            ValidationRuleParam param = existingParam.get();
            param.setParamValue(updateRequest.getParamValue());
            return validationRuleParamRepo.save(param);
        } else {
            throw new IllegalArgumentException("Validation rule parameter not found");
        }
    }

}
