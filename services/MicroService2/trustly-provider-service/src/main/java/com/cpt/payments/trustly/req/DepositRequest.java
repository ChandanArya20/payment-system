package com.cpt.payments.trustly.req;

import com.cpt.payments.pojo.PaymentResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepositRequest {
    
	@JsonProperty("method")
    private String method;

    @JsonProperty("params")
    private Params params;

    @JsonProperty("version")
    private String version;

}