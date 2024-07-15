package com.cpt.payments.dao;

import com.cpt.payments.DTO.UserDTO;

public interface UserDao {
    boolean userExists(String endUserId);

    void insertUser(UserDTO user);

    void logAndUpdateUserIfRequired(UserDTO user);
}
