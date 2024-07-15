package com.cpt.payments.constant;

import com.cpt.payments.service.impl.validators.DuplicateTnxValidator;
import com.cpt.payments.service.impl.validators.PaymentAttemptThresholdValidator;
import com.cpt.payments.service.impl.validators.RequestStructureValidator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ValidationRules {

    REQUEST_STRUCTURE_VALIDATION("REQUEST_STRUCTURE_VALIDATION", RequestStructureValidator.class),
    DUPLICATE_TXN_REF_VALIDATION("DUPLICATE_TXN_REF_VALIDATION", DuplicateTnxValidator.class),
    PAYMENT_ATTEMPT_LIMIT_EXCEEDED_VALIDATION("PAYMENT_ATTEMPT_LIMIT_EXCEEDED_VALIDATION", PaymentAttemptThresholdValidator.class);

    private final String name;
    private final Class<?> clazz;

    public static ValidationRules fromName(String name) {
        for (ValidationRules type : ValidationRules.values()) {
            if (type.getName().equalsIgnoreCase(name)) {
                return type;
            }
        }

        return null;
    }

}
