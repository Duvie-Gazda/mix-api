package com.mix.api.controller;

import com.mix.api.model.User;
import com.mix.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    public User getUserById(Long id){
        return userRepository.getById(id);
    }

}
