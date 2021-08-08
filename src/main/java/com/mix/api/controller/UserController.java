package com.mix.api.controller;

import com.mix.api.controller.helper_classes.UserCreate;
import com.mix.api.model.User;
import com.mix.api.repository.UserRepository;
import com.mix.api.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @GetMapping(path = "/user/{id}")
    public User getUserById(@PathVariable Long id){
        return userRepository.getById(id);
    }

    @GetMapping(path = "/user/{nick}")
    public User getUserByNick(@PathVariable String nick){ return userRepository.getUserByNick(nick);}

    @PostMapping(path = "/user/add")
    public Boolean addUser(@RequestBody UserCreate user){
        try{
            userRepository.save(user.getUser());
            userRoleRepository.save(user.getRole());
            return true;
        } catch (Throwable throwable){
            return  false;
        }
    }

    @DeleteMapping(path = "/user/{nick}")
    public Boolean deleteUser(@PathVariable String nick){
        try {
            userRepository.delete(userRepository.getUserByNick(nick));
            return true;
        }catch (Throwable throwable){
            return false;
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
