package com.cpt.payments.service.processor;

import org.junit.jupiter.api.Test;

import com.cpt.payments.trustly.req.Attributes;
import com.cpt.payments.utils.GsonFieldNamingStrategyJsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class RequestProcessorTest {
	
	@Test
	public void testMethod() {
		
		Attributes myAttributes = new Attributes();
		myAttributes.setCountry("IND");
		myAttributes.setLocale("en_IN");
		
		
		Attributes myAttributes1 = Attributes.builder()
				.country("IND")
				.locale("en_IN")
				.build();
		
		
		Gson gson = new GsonBuilder()
                .setFieldNamingStrategy(
                		new GsonFieldNamingStrategyJsonProperty())
                .create();
		
		String jsonString = gson.toJson(myAttributes);
		
		System.out.println("myAttributes:" + myAttributes);
		System.out.println("jsonString:" + jsonString);
		
		System.out.println("Executing TestMethod");
	}

}
