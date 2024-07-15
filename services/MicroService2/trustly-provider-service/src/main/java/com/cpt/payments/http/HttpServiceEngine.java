package com.cpt.payments.http;

import org.springframework.http.ResponseEntity;

public interface HttpServiceEngine {

	public ResponseEntity<String> execute(HttpRequest httpRequest);
}
