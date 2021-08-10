package com.mix.api.controller;

import com.mix.api.controller.dto.FastUserDto;
import com.mix.api.controller.service.UserService;
import com.mix.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping(path = "/users/{nick}")
    public User getUserByNicName(@PathVariable String nick){
        return userService.getUserByNick(nick);
    }



}