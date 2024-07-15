package com.cpt.payments.constant;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentAttemptThreshold {
    SHORT_TERM_LIMIT(2, 5),
    HOURLY_LIMIT(5, 60);

    private final int maxAttempts;
    private final int timeFrameMinutes;
}
