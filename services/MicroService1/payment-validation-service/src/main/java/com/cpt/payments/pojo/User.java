package com.cpt.payments.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class User {
    private String endUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
