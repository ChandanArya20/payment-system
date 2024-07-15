package com.cpt.payments.service.impl.validators;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.constant.PaymentAttemptThreshold;
import com.cpt.payments.dao.MerchantPaymentDao;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.service.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class PaymentAttemptThresholdValidator implements Validator {

    @Autowired
    private MerchantPaymentDao paymentAttemptDao;

    @Override
    public void doValidate(PaymentRequestDTO request) {
        System.out.println("PaymentAttemptThresholdValidator.doValidate | request = " + request);

        String endUserId = request.getUser().getEndUserId();

        if (endUserId == null || endUserId.trim().isEmpty()) {
            System.out.println("endUserId is null or empty");

            ValidationException valEx = new ValidationException(
                    ErrorCodeEnum.END_USER_ID_EMPTY.getErrorCode(),
                    ErrorCodeEnum.END_USER_ID_EMPTY.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);

            System.out.println("Throwing exception valEx:" + valEx);

            throw valEx;
        }

        for (PaymentAttemptThreshold threshold : PaymentAttemptThreshold.values()) {
            paymentAttemptDao.validateThreshold(endUserId, threshold);
        }

        paymentAttemptDao.logPaymentAttempt(request.getUser(), request.getPayment());

        System.out.println("PaymentAttemptThresholdValidator passed successfully");
    }
}
