package com.cpt.payments.service;

import com.cpt.payments.DTO.ValidationRuleResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class RedisService {

    private RedisTemplate<String, Object> redisTemplate;
    private ValidationRuleService validationRuleService;
    private ObjectMapper objectMapper;

    public void save(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public Object find(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    public boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }

    @PostConstruct
    public void loadValidationRulesIntoCache() throws JsonProcessingException {
        HashOperations<String, String, String> opsForHash = redisTemplate.opsForHash();
        ListOperations<String, Object> opsForList = redisTemplate.opsForList();

        if (!exists("validationRules")) {
            List<ValidationRuleResponse> validationRules = validationRuleService.getAllValidationRulesWithParams();

            List<Object> validationRuleNames = new ArrayList<>();

            for (ValidationRuleResponse rule : validationRules) {
                validationRuleNames.add(rule.getValidatorName());
            }

            opsForList.leftPushAll("validationRules", validationRuleNames);

            for (ValidationRuleResponse rule : validationRules) {
                rule.getParams().forEach((key, value) -> opsForHash.put(rule.getValidatorName(), key, value));
            }
        }
    }
}
