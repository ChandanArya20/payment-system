package com.cpt.payments.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.cpt.payments.utils.GsonFieldNamingStrategyJsonProperty;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import javax.xml.transform.Templates;

@Configuration
public class AppConfig {

	@Bean
	public ModelMapper getModelMapper() {
		return new ModelMapper();
	}
	
	@Bean
	public Gson createGson() {
        return new GsonBuilder()
                .setFieldNamingStrategy(new GsonFieldNamingStrategyJsonProperty())
                .create();
    }

	@Bean
	public RestTemplate restTemplate(){
//		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
//		factory.setConnectTimeout(5000);
//		factory.setReadTimeout(5000);

//		return new RestTemplate(factory);
		return new RestTemplate();
	}

}
