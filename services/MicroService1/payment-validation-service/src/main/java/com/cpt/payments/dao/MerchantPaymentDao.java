package com.cpt.payments.dao;

import com.cpt.payments.DTO.MerchantPaymentRequest;
import com.cpt.payments.DTO.PaymentDTO;
import com.cpt.payments.DTO.UserDTO;
import com.cpt.payments.DTO.UserLogDTO;
import com.cpt.payments.constant.PaymentAttemptThreshold;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

public interface MerchantPaymentDao {

    void insertMerchantPaymentRequest(String merchantTransactionReference, String transactionRequest);

    MerchantPaymentRequest findByMerchantTransactionReference(String merchantTransactionReference);

    boolean validateThreshold(String endUserId, PaymentAttemptThreshold threshold);

    void logPaymentAttempt(UserDTO user, PaymentDTO payment);
}
