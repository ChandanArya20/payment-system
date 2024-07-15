package com.cpt.payments.dto;

import lombok.Data;

@Data
public class UserDTO {
    private String endUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
