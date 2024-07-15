package com.cpt.payments.config;

import com.cpt.payments.security.ExceptionHandlerFilter;
import com.cpt.payments.security.HmacSHA256Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

//	@Autowired
//	HmacSHA256Filter hmacSHA256Filter;

	@Autowired
	ExceptionHandlerFilter exceptionHandlerFilter;

	@Bean
	public SecurityFilterChain configure(HttpSecurity http) throws Exception {
		
		http
	    .csrf(csrf -> csrf.disable())
	    .authorizeHttpRequests(
//				authorize -> authorize.anyRequest().authenticated()
				authorize -> authorize.anyRequest().permitAll() //TODO only fot testing , remove before commit
		)

		.addFilterBefore(exceptionHandlerFilter, AuthorizationFilter.class)

//		.addFilterBefore(hmacSHA256Filter, AuthorizationFilter.class)

	    .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

		return http.build();
	}
}
