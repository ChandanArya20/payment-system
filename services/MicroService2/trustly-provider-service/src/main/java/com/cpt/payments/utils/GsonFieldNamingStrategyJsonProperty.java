package com.cpt.payments.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.FieldNamingStrategy;

import java.lang.reflect.Field;

public class GsonFieldNamingStrategyJsonProperty implements FieldNamingStrategy {
    @Override
    public String translateName(Field field) {
        JsonProperty annotation = field.getAnnotation(JsonProperty.class);
        return annotation != null ? annotation.value() : field.getName();
    }
}
