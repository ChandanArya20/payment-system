package com.cpt.payments.trustly.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@lombok.Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Data {
	
	@JsonProperty("Username")
    private String username;

    @JsonProperty("Password")
    private String password;

    @JsonProperty("NotificationURL")
    private String notificationURL;

    @JsonProperty("EndUserID")
    private String endUserID;

    @JsonProperty("MessageID")
    private String messageID;

    @JsonProperty("Attributes")
    private Attributes attributes;

}
