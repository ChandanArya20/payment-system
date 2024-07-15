package com.cpt.payments.security;

import org.springframework.security.authentication.AbstractAuthenticationToken;

import java.util.List;

public class CustomAuthToken extends AbstractAuthenticationToken {

    public CustomAuthToken() {
        super(List.of());
        super.setAuthenticated(true);
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
