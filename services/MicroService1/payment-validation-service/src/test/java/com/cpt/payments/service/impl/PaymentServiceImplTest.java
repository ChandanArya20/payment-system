package com.cpt.payments.service.impl;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.DTO.PaymentResponseDTO;
import com.cpt.payments.service.Validator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationContext;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PaymentServiceImplTest {

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    ApplicationContext appContext;

    @Mock
    Validator validationBean;

    @Test
    void validateAndInitiate() {
        System.out.println("PaymentServiceImpl.validateAndInitiate() paymentService: " + paymentService);

        //Arrange data
        ReflectionTestUtils.setField(paymentService, "validatorRules", "REQUEST_STRUCTURE_VALIDATION,VALIDATION_CHECK2");
        when(appContext.getBean(any(Class.class))).thenReturn(validationBean);

        PaymentResponseDTO responseDTO = paymentService.validateAndInitiate(null);

        //verification
        assertNotNull(responseDTO);
        assertEquals("https://www.google.com/", responseDTO.getRedirectURL());
        verify(validationBean, times(2)).doValidate(null);

    }


}