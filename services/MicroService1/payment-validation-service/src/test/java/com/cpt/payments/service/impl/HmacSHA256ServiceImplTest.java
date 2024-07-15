package com.cpt.payments.service.impl;

import com.cpt.payments.pojo.Payment;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.pojo.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;

public class HmacSHA256ServiceImplTest {

    HmacSHA256ServiceImpl service = new HmacSHA256ServiceImpl();

    @Test
    public void testMethod() throws JsonProcessingException {

        // Create User object and set data
        User user = new User();
        user.setEndUserId("123456");
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        user.setPhone("+1234567890");

        // Create Payment object and set data
        Payment payment = new Payment();
        payment.setMerchantTxnRef("txn123456");
        payment.setCurrency("USD");
        payment.setCountry("US");
        payment.setLocale("en_US");
        payment.setShopperStatement("Purchase at Example Shop");
        payment.setSuccessURL("https://example.com/success");
        payment.setFailureUrl("https://example.com/failure");
        payment.setAmount("100.00");
        payment.setPaymentMethod("CreditCard");
        payment.setPaymentType("SALE");
        payment.setProviderId("Trustly");

        // Create PaymentRequest object and set user and payment
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setUser(user);
        paymentRequest.setPayment(payment);

        // Convert PaymentRequest object to JSON string
        Gson gson = new Gson();
        String paymentAsJson = gson.toJson(paymentRequest);

        String signature = service.generateSignature(paymentAsJson);
        System.out.println("Generated signature : "+signature);
    }
}
