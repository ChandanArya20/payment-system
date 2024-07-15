package com.cpt.payments.service.impl;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.service.HmacSHA256Service;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class HmacSHA256ServiceImpl implements HmacSHA256Service {

    private final String secreteKey = "This is my secrete key";

    @Override
    public String generateSignature(String data)  {
        System.out.println("HmacSHA256ServiceImpl.generateSignature | data: "+data);
        try {
            // Create a new secret key
            SecretKeySpec secretKeySpec = new SecretKeySpec(secreteKey.getBytes(), "HmacSHA256");

            // Create a Mac instance and initialize it with the secret key
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(secretKeySpec);

            // Compute the HMAC hash
            byte[] rawHmac = mac.doFinal(data.getBytes());

            // Convert to Base64 string
            return Base64.getEncoder().encodeToString(rawHmac);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            System.out.println("Error in generating signature");
            e.printStackTrace();
            throw new ValidationException(
                    ErrorCodeEnum.SIGNATURE_GENERATION_ERROR.getErrorCode(),
                    ErrorCodeEnum.SIGNATURE_GENERATION_ERROR.getErrorMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR
            );
        }
    }

    @Override
    public boolean verifySignature(String data, String incomingSignature) {
        String signature = generateSignature(data);
        return signature.equals(incomingSignature);
    }
}
