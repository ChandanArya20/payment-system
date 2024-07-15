package com.cpt.payments.service.impl.validators;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.service.Validator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
public class RequestStructureValidator implements Validator {

    @Override
    public void doValidate(PaymentRequestDTO request) {
        System.out.println("RequestStructureValidator.doValidate | request = " + request);

        if (request == null || request.getPayment() == null || request.getUser() == null) {
            System.out.println("Invalid request structure, received null value");

            ValidationException valEx = new ValidationException(
                    ErrorCodeEnum.REQUEST_STRUCTURE_INVALID.getErrorCode(),
                    ErrorCodeEnum.REQUEST_STRUCTURE_INVALID.getErrorMessage(),
                    HttpStatus.BAD_REQUEST);

            System.out.println("RequestStructureValidator validation failed. Throwing exception valEx:" + valEx);

            throw valEx;
        }
        System.out.println("RequestStructureValidator passed successfully");
    }
}
