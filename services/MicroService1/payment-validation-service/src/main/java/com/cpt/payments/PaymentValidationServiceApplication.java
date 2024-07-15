package com.cpt.payments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PaymentValidationServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(PaymentValidationServiceApplication.class, args);
		System.out.println("application started");
	}

}
