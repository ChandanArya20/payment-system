package com.cpt.payments.security;

import com.cpt.payments.constant.ErrorCodeEnum;
import com.cpt.payments.exception.ValidationException;
import com.cpt.payments.pojo.PaymentErrorResponse;
import com.google.gson.Gson;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class ExceptionHandlerFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            System.out.println("ExceptionHandlerFilter Before doFilter");
            filterChain.doFilter(request, response);
            System.out.println("ExceptionHandlerFilter After doFilter");

        } catch (ValidationException ex) {

            System.out.println("validation exception is -> " + ex.getErrorMessage());
            PaymentErrorResponse paymentResponse = new PaymentErrorResponse
                    (ex.getErrorCode(), ex.getErrorMessage());
            System.out.println("paymentResponse is -> " + paymentResponse);

            Gson gson = new Gson();
            response.setStatus(ex.getStatus().value());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(paymentResponse));
            response.getWriter().flush();

        } catch (Exception ex) {

            System.out.println(" generic exception message is -> " + ex.getMessage());
            PaymentErrorResponse paymentResponse = new PaymentErrorResponse
                    (ErrorCodeEnum.GENERIC_ERROR.getErrorCode()
                            , ErrorCodeEnum.GENERIC_ERROR.getErrorMessage());
            System.out.println(" paymentResponse is -> " + paymentResponse);

            Gson gson = new Gson();
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            response.setContentType("application/json");
            response.getWriter().write(gson.toJson(paymentResponse));
            response.getWriter().flush();
        }
    }
}
