package com.cpt.payments.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public interface HmacSHA256Service {
    public String generateSignature(String data) throws NoSuchAlgorithmException, InvalidKeyException;
    public boolean verifySignature(String data, String incomingSignature);
}
