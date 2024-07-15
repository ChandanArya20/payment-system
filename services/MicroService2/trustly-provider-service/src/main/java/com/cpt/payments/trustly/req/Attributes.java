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
public class Attributes {
	
	@JsonProperty("Country")
    private String country;

    @JsonProperty("Locale")
    private String locale;

    @JsonProperty("Currency")
    private String currency;

    @JsonProperty("Amount")
    private String amount;

    @JsonProperty("IP")
    private String ip;

    @JsonProperty("MobilePhone")
    private String mobilePhone;

    @JsonProperty("Firstname")
    private String firstname;

    @JsonProperty("Lastname")
    private String lastname;

    @JsonProperty("Email")
    private String email;

    @JsonProperty("NationalIdentificationNumber")
    private String nationalIdentificationNumber;

    @JsonProperty("SuccessURL")
    private String successURL;

    @JsonProperty("FailURL")
    private String failURL;
}
