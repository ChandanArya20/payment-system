package com.cpt.payments.dao.impl;

import com.cpt.payments.DTO.UserDTO;
import com.cpt.payments.DTO.UserLogDTO;
import com.cpt.payments.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
class UserDaoImpl implements UserDao {

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public boolean userExists(String endUserId) {
        System.out.println("UserDaoImpl.userExists | endUserId = " + endUserId);

        String sql = "SELECT COUNT(*) FROM user WHERE endUserId = :endUserId";
        Map<String, Object> params = new HashMap<>();
        params.put("endUserId", endUserId);

        Integer count = jdbcTemplate.queryForObject(sql, params, Integer.class);
        return count != null && count > 0;
    }

    @Override
    public void insertUser(UserDTO user) {
        System.out.println("UserDaoImpl.insertUser | userDTO = " + user);

        String sql = "INSERT INTO user (endUserId, firstName, lastName, email, phone) VALUES (:endUserId, :firstName, :lastName, :email, :phone)";

        Map<String, Object> params = new HashMap<>();
        params.put("endUserId", user.getEndUserId());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("email", user.getEmail());
        params.put("phone", user.getPhone());

        System.out.println("Inserting or updating user with id " + user.getEndUserId());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public void logAndUpdateUserIfRequired(UserDTO user) {
        System.out.println("UserDaoImpl.updateUserIfRequired | user = " + user);

        if (userExists(user.getEndUserId())) {
            System.out.println("user is available with id " + user.getEndUserId());

            UserDTO existingUser = getUserById(user.getEndUserId());
            if (!existingUser.equals(user)) {
                System.out.println("User data is diff from what is there in db");

                UserLogDTO userLog = new UserLogDTO();
                userLog.setEndUserId(existingUser.getEndUserId());
                userLog.setFirstName(existingUser.getFirstName());
                userLog.setLastName(existingUser.getLastName());
                userLog.setEmail(existingUser.getEmail());
                userLog.setPhone(existingUser.getPhone());

                insertUserLog(userLog);
                updateUser(user);
            }
        }
    }

    public void updateUser(UserDTO user) {
        System.out.println("UserDaoImpl.updateUser | userDTO = " + user);

        String sql = "UPDATE user SET firstName = :firstName, lastName = :lastName, email = :email, phone = :phone WHERE endUserId = :endUserId";

        Map<String, Object> params = new HashMap<>();
        params.put("endUserId", user.getEndUserId());
        params.put("firstName", user.getFirstName());
        params.put("lastName", user.getLastName());
        params.put("email", user.getEmail());
        params.put("phone", user.getPhone());

        System.out.println("Updating user with id " + user.getEndUserId());
        jdbcTemplate.update(sql, params);
    }


    private void insertUserLog(UserLogDTO userLog) {
        System.out.println("UserDaoImpl.insertUserLog | userLogDTO = " + userLog);

        String sql = "INSERT INTO user_log (endUserId, firstName, lastName, email, phone) VALUES (:endUserId, :firstName, :lastName, :email, :phone)";
        Map<String, Object> params = new HashMap<>();
        params.put("endUserId", userLog.getEndUserId());
        params.put("firstName", userLog.getFirstName());
        params.put("lastName", userLog.getLastName());
        params.put("email", userLog.getEmail());
        params.put("phone", userLog.getPhone());

        jdbcTemplate.update(sql, params);
    }

    private UserDTO getUserById(String endUserId) {
        String sql = "SELECT * FROM user WHERE endUserId = :endUserId";
        Map<String, Object> params = new HashMap<>();
        params.put("endUserId", endUserId);

        return jdbcTemplate.queryForObject(sql, params, (rs, rowNum) -> {
            UserDTO user = new UserDTO();
            user.setEndUserId(rs.getString("endUserId"));
            user.setFirstName(rs.getString("firstName"));
            user.setLastName(rs.getString("lastName"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));
            return user;
        });
    }

}