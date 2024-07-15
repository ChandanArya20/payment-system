package com.cpt.payments.DTO;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@EqualsAndHashCode
public class UserDTO {
    private String endUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
}
