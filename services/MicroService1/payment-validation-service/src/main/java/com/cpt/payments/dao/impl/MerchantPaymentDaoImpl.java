package com.cpt.payments.dao.impl;

import com.cpt.payments.DTO.MerchantPaymentRequest;
import com.cpt.payments.DTO.PaymentDTO;
import com.cpt.payments.DTO.UserDTO;
import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.constant.PaymentAttemptThreshold;
import com.cpt.payments.dao.MerchantPaymentDao;
import com.cpt.payments.dao.UserDao;
import com.cpt.payments.exception.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@Repository
public class MerchantPaymentDaoImpl implements MerchantPaymentDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    private UserDao userDao;

    @Override
    public void insertMerchantPaymentRequest(String merchantTransactionReference,
                                             String transactionRequest) {
        System.out.println("merchantTransactionReference = " + merchantTransactionReference + ", transactionRequest = " + transactionRequest);

        String sql = "INSERT INTO validations.merchant_payment_request (merchantTransactionReference, transactionRequest) VALUES (:merchantTransactionReference, :transactionRequest)";
        Map<String, String> params = new HashMap<>();
        params.put("merchantTransactionReference", merchantTransactionReference);
        params.put("transactionRequest", transactionRequest);

        try {
            int rowCount = jdbcTemplate.update(sql, params);

            if (rowCount == 1) {
                System.out.println("Data inserted in DB");
            } else {
                System.out.println("Enable to insert into DB");
            }
        } catch (DuplicateKeyException e) {
            System.out.println("Enable to save merchantReq in db, got exception : " + e);

            ValidationException valEx = new ValidationException(
                    ErrorCodeEnum.DUPLICATE_TXN_ERROR.getErrorCode(),
                    ErrorCodeEnum.DUPLICATE_TXN_ERROR.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);

            System.out.println("Throwing exception valEx:" + valEx);

            throw valEx;
        }
    }

    public MerchantPaymentRequest findByMerchantTransactionReference(String merchantTransactionReference) {
        System.out.println("merchantTransactionReference = " + merchantTransactionReference);

        String sql = "SELECT * FROM validations.merchant_payment_request WHERE merchantTransactionReference=:merchantTransactionReference";

        Map<String, String> params = new HashMap<>();
        params.put("merchantTransactionReference", merchantTransactionReference);
        MerchantPaymentRequest merchantPaymentRequest = jdbcTemplate.queryForObject(sql, params, new BeanPropertyRowMapper<>(MerchantPaymentRequest.class));

        System.out.println("Object from db : " + merchantPaymentRequest);

        return merchantPaymentRequest;
    }

    public boolean validateThreshold(String endUserId, PaymentAttemptThreshold threshold) {
        System.out.println("MerchantPaymentDaoImpl.validateThreshold | endUserId = " + endUserId + ", threshold = " + threshold);

        Timestamp currentTime = Timestamp.from(Instant.now());
        Timestamp startTime = Timestamp.from(Instant.now().minusSeconds(threshold.getTimeFrameMinutes() * 60L));

        int attemptCount = countPaymentAttempts(endUserId, startTime, currentTime);

        if (attemptCount >= threshold.getMaxAttempts()) {
            System.out.println("Payment attempt threshold exceeded for user: " + endUserId);

            ValidationException valEx = new ValidationException(
                    ErrorCodeEnum.PAYMENT_ATTEMPT_LIMIT_EXCEEDED.getErrorCode(),
                    ErrorCodeEnum.PAYMENT_ATTEMPT_LIMIT_EXCEEDED.getErrorMessage(),
                    HttpStatus.TOO_MANY_REQUESTS);

            System.out.println("Throwing exception valEx:" + valEx);

            throw valEx;
        }

        return true;
    }

    private int countPaymentAttempts(String endUserId, Timestamp startTime, Timestamp endTime) {
        System.out.println("MerchantPaymentDaoImpl.countPaymentAttempts | endUserId = " + endUserId + ", startTime = " + startTime + ", endTime = " + endTime);

        String sql = "SELECT COUNT(*) FROM payment WHERE endUserId = :endUserId AND creationDate BETWEEN :startTime AND :endTime";
        Map<String, Object> params = new HashMap<>();
        params.put("endUserId", endUserId);
        params.put("startTime", startTime);
        params.put("endTime", endTime);

        Integer attempts = jdbcTemplate.queryForObject(sql, params, Integer.class);
        System.out.println("Payment attempts for user " + endUserId + " is " + attempts);
        return attempts;
    }

    @Override
    public void logPaymentAttempt(UserDTO user, PaymentDTO payment) {
        System.out.println("MerchantPaymentDaoImpl.logPaymentAttempt | payment = " + payment);

        if (!userDao.userExists(user.getEndUserId())) {
            System.out.println("User doesn't exist, so inserting in db");
            userDao.insertUser(user);
        } else {
            System.out.println("offering user to update");
            userDao.logAndUpdateUserIfRequired(user);
        }

        String sql = "INSERT INTO payment (endUserId, merchantTxnRef, amount, currency, country, locale, shopperStatement, successURL, failureURL, paymentMethod, paymentType, providerId) VALUES (:endUserId, :merchantTxnRef, :amount, :currency, :country, :locale, :shopperStatement, :successURL, :failureURL, :paymentMethod, :paymentType, :providerId)";

        Map<String, Object> params = new HashMap<>();
        params.put("endUserId", user.getEndUserId());
        params.put("merchantTxnRef", payment.getMerchantTxnRef());
        params.put("amount", payment.getAmount());
        params.put("currency", payment.getCurrency());
        params.put("country", payment.getCountry());
        params.put("locale", payment.getLocale());
        params.put("shopperStatement", payment.getShopperStatement());
        params.put("successURL", payment.getSuccessURL());
        params.put("failureURL", payment.getFailureUrl());
        params.put("paymentMethod", payment.getPaymentMethod());
        params.put("paymentType", payment.getPaymentType());
        params.put("providerId", payment.getProviderId());


        int rowCount = jdbcTemplate.update(sql, params);
        if (rowCount == 1) {
            System.out.println("saved payment successfully in db");
        } else {
            System.out.println("payment filed to be saved in db");
        }
    }
}
