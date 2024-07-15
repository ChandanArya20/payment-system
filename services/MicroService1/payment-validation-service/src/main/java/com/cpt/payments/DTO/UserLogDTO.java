package com.cpt.payments.DTO;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Setter
@Getter
@ToString
public class UserLogDTO {
    private String endUserId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;

}
