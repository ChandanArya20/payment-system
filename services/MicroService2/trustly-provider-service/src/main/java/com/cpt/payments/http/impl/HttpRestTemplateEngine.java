package com.cpt.payments.http.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;

import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.http.HttpServiceEngine;
import org.springframework.web.client.*;

import java.util.Collections;

@Component
public class HttpRestTemplateEngine implements HttpServiceEngine {

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public ResponseEntity<String> execute(HttpRequest httpRequest) {
		System.out.println("HttpRestTemplateEngine.execute | httpRequest = " + httpRequest);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
//		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> requestEntity = new HttpEntity<>(httpRequest.getRequestBody(), headers);

		try {
			ResponseEntity<String> response = restTemplate.postForEntity(httpRequest.getUrl(), requestEntity, String.class);

			System.out.println(response.getStatusCode());
			System.out.println(response.getBody());

			if (response.getStatusCode().is2xxSuccessful()){
				System.out.println("API is successful with 2xx status code");
				return response;
			}else {
				System.out.println("Didn't Got 2xx, returning exception");
				return createCustomErrorResponse(response.getStatusCode(), response.getBody(),response.getHeaders());
			}

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			System.out.println("Got 4xx or 5xx status code , returning exception e "+e);
			return createCustomErrorResponse(e.getStatusCode(), e.getResponseBodyAsString(), e.getResponseHeaders());

		} catch (ResourceAccessException e) {
			System.out.println("Network error: "+e.getMessage());
			return createCustomErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, "Network error occurred", null);
		} catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}

	private ResponseEntity<String> createCustomErrorResponse(HttpStatusCode statusCode, String resBody, HttpHeaders headers) {
		if (headers == null) {
			headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
		}

		return new ResponseEntity<>(resBody, headers, statusCode);
	}

}
