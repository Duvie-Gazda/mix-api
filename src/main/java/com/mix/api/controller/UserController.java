package com.mix.api.controller;

import com.mix.api.controller.dto.UserDto;
import com.mix.api.model.User;
import com.mix.api.repository.UserRepository;
import com.mix.api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {
    @Autowired
     private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @PostMapping(path = "/users/new")
    public ResponseEntity<?> createUser(@RequestBody UserDto userDto){
        try{
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Throwable throwable){
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping(path = "/users/{id}")
    public User getUser (@PathVariable Long id){
        return userRepository.findUserById(id);
    }



    @DeleteMapping(path = "/users/{nick}")
    public ResponseEntity<?> deleteUser(@PathVariable String nick){
        try {
            userRepository.delete(userRepository.findUserByNick(nick));
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        }catch (Throwable throwable){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }

    @DeleteMapping(path = "/users")
    public Boolean deleteUsers(@RequestBody List<Long> user_ids){
        try{
            for (Long id:user_ids) {
                userRepository.delete(userRepository.getById(id));
            }
            return true;
        } catch (Throwable throwable){
            return false;
        }
    }




}