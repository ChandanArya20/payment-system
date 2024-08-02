package com.cpt.payments.controller;

import com.cpt.payments.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/redis")
@RequiredArgsConstructor
public class RedisController {

    private final RedisService redisService;

    @PostMapping("/load")
    public String loadValidationRulesIntoCache() {
        try {
            redisService.loadValidationRulesIntoCache();
            return "Validation rules loaded into cache successfully.";
        } catch (JsonProcessingException e) {
            return "Error loading validation rules into cache: " + e.getMessage();
        }
    }

    @GetMapping("/validation-rules")
    public List<String> getValidationRulesFromCache() {
        return redisService.getValidationRulesFromCache();
    }
}
