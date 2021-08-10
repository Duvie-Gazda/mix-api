package com.mix.api.controller.service;

import com.mix.api.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    @Test
    void createUser() {
        UserService service = new UserService();
        HttpStatus res = service.createUser(new User());
        assertEquals (HttpStatus.OK, res);
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUserRoleByRoleId() {
    }

    @Test
    void getUserDataByDataType() {
    }
}