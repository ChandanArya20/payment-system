package com.cpt.payments.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface HmacSHA256Service {
    String generateSignature(String data) throws NoSuchAlgorithmException, InvalidKeyException;
    boolean verifySignature(String data, String incomingSignature);
}
