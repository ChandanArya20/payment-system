package com.cpt.payments.trustly.res.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Error {
    private String name;
    private int code;
    private String message;
    private ErrorDetails error;
}
