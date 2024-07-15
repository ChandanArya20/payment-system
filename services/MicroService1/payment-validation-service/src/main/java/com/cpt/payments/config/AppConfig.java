package com.cpt.payments.config;

import com.cpt.payments.DTO.PaymentRequestDTO;
import com.cpt.payments.DTO.PaymentResponseDTO;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

    @Bean
    public Gson gson(){
        return new Gson();
    }


}
