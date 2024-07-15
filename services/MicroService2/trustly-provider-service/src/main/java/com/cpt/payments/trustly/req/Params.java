package com.cpt.payments.trustly.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Params {
	
	@JsonProperty("Signature")
	private String signature;

	@JsonProperty("UUID")
	private String uuid;

	@JsonProperty("Data")
	private Data data;

}
