package com.cpt.payments.service.impl.validators;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.dao.MerchantPaymentDao;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.service.Validator;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
public class DuplicateTnxValidator implements Validator {

    @Autowired
    private MerchantPaymentDao merchantPaymentDao;

    @Autowired
    private Gson gSon;

    @Override
    public void doValidate(PaymentRequestDTO request) {
        System.out.println("DuplicateTnxValidator.doValidate | request = " + request);

        String merchantTxnRef = request.getPayment().getMerchantTxnRef();

        if(merchantTxnRef==null){
            merchantTxnRef="";
        }
        merchantTxnRef = merchantTxnRef.trim();

        if(merchantTxnRef.isEmpty()){
            System.out.println("merchantTxnReference received as null or empty");

            ValidationException valEx = new ValidationException(
                    ErrorCodeEnum.MERCHANT_TXN_REF_EMPTY.getErrorCode(),
                    ErrorCodeEnum.MERCHANT_TXN_REF_EMPTY.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);

            System.out.println("Throwing exception valEx:" + valEx);

            throw valEx;
        }

        String transactionRequest = gSon.toJson(request);
        merchantPaymentDao.insertMerchantPaymentRequest(merchantTxnRef, transactionRequest);

        System.out.println("DuplicateTnxValidator passed successfully");
    }
}
