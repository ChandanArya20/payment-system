package com.cpt.payments.security;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.pojo.PaymentRequest;
import com.cpt.payments.service.HmacSHA256Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.BufferedReader;
import java.io.IOException;

//@Component
public class HmacSHA256Filter extends OncePerRequestFilter {

    @Autowired
    HmacSHA256Service hmacSHA256Service;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("HmacSHA256Filter.doFilterInternal()");

        String signature = request.getHeader("signature");
        
        //reads the data from request body
        WrappedRequest wrappedRequest = new WrappedRequest(request);
        String requestBody = wrappedRequest.getBody();

        String reqAsJson = objectMapper.writeValueAsString(objectMapper.readValue(requestBody, PaymentRequest.class));
        boolean isSignatureValid = hmacSHA256Service.verifySignature(reqAsJson, signature);

        if (isSignatureValid) {
            //sets custom AuthenticationToken to SecurityContextHolder
            //so that AuthorizationFilter can read and make decision
            CustomAuthToken authToken = new CustomAuthToken();
            SecurityContextHolder.getContext().setAuthentication(authToken);

            filterChain.doFilter(wrappedRequest, response);
            return;
        }

        System.out.println("Signature invalid, throwing exception");
        throw new ValidationException(
                ErrorCodeEnum.SIGNATURE_INVALID_ERROR.getErrorCode(),
                ErrorCodeEnum.SIGNATURE_INVALID_ERROR.getErrorMessage(),
                HttpStatus.BAD_REQUEST
        );
    }
}
