package com.cpt.payments.service.processor;

import com.cpt.payments.constants.Constants;
import com.cpt.payments.dto.PaymentRequestDTO;
import com.cpt.payments.http.HttpRequest;
import com.cpt.payments.service.RSAService;
import com.cpt.payments.trustly.req.Attributes;
import com.cpt.payments.trustly.req.Data;
import com.cpt.payments.trustly.req.DepositRequest;
import com.cpt.payments.trustly.req.Params;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;

@Component
public class RequestProcessor {

    @Autowired
    private Gson gson;

    @Autowired
    private RSAService rSAService;

    public HttpRequest prepareRequest(PaymentRequestDTO request) {

        Attributes attributesRef = Attributes.builder()
                .country(request.getPayment().getCountry())
                .locale(request.getPayment().getLocale())
                .currency(request.getPayment().getCurrency())
                .amount(request.getPayment().getAmount())
                .mobilePhone(request.getUser().getPhone())
                .firstname(request.getUser().getFirstName())
                .lastname(request.getUser().getLastName())
                .email(request.getUser().getEmail())
                .successURL(request.getPayment().getSuccessURL())
                .failURL(request.getPayment().getFailureUrl())
                .build();


        Data data = Data.builder()
                .username("UNAME")//TODO store & access from AWS Secret Manager
                .password("PWD")//TODO store & access from AWS Secret Manager
                .notificationURL(request.getPayment().getNotificationUrl())
                .endUserID(request.getUser().getEndUserId())
                .messageID(request.getPayment().getTransactionReference())
                .attributes(attributesRef)
                .build();

        //Generate RSA Signature & pass to Trustly
        String signature = null;
        try {
            // Read private key from PEM file
            String privateKeyPEM = rSAService.readPrivateKeyPEMFile(".\\src\\main\\resources\\ecom_private.pem");

            String jsonString = gson.toJson(data);
            System.out.println("jsonString:" + jsonString);

            signature = rSAService.sign(Constants.TRUSTLY_DEPOSIT,
                    request.getPayment().getTransactionReference(),
                    jsonString,
                    privateKeyPEM);

            System.out.println("signature generated successfully"
                    + "||signature:" + signature);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(); //TODO
        }

        Params params = Params.builder()
                .signature(signature)
                .uuid(request.getPayment().getTransactionReference())
                .data(data)
                .build();


        DepositRequest depositReq = DepositRequest.builder()
                .method(Constants.TRUSTLY_DEPOSIT)
                .params(params)
                .version(Constants.TRUSTLY_API_VERSION)
                .build();


        System.out.println("depositReq:" + depositReq);

        String jsonString = gson.toJson(depositReq);

        System.out.println("jsonString:" + jsonString);

        HttpRequest httpRequest = HttpRequest.builder()
                .url("http://localhost:8084/payment/initiate")
                .requestBody(jsonString)
                .method(HttpMethod.POST)
                .build();

        System.out.println("httpRequest:" + httpRequest);

        return httpRequest;
    }
}
