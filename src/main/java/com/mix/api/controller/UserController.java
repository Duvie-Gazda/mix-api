package com.mix.api.controller;

import com.mix.api.controller.service.UserService;
import com.mix.api.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


@RestController
public class UserController {
    @Autowired
    private UserService userService;

//    GET

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping(path = "/users/{nick}")
    public User getUserByNicName(@PathVariable String nick){
        return userService.getUserByNick(nick);
    }

    @GetMapping(path = "/users/{userDataName}")
    public Set<User> getUsersByUserDataName(@PathVariable String userDataName){
        return userService.getUsersByUserDataName(userDataName);
    }

    @GetMapping(path = "/users/{userRoleId}")
    public Set<User> getUserByUserRoleId(@PathVariable Long userRoleId){
        return userService.getUsersByRoleId(userRoleId);
    }

    @GetMapping(path = "/users/{userRoleName}")
    public Set<User> getUserByUserRoleName(@PathVariable String userRoleName){
        return userService.getUsersByRoleName(userRoleName);
    }

//    POST

    @PostMapping(path = "/users/update")
    public ResponseEntity<?> updateUser(@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user));
    }

    @PostMapping(path = "/users")
    public ResponseEntity<?> createUser(@RequestBody User user){
        return new ResponseEntity<>(userService.createUser(user));
    }

//    DELETE

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUser(userService.getUserById(id)));
    }

    @DeleteMapping(path = "/users/{nick}")
    public ResponseEntity<?> deleteUserByNick(@PathVariable String nick){
        return new ResponseEntity<>(userService.deleteUser(userService.getUserByNick(nick)));
    }

    @DeleteMapping(path = "/users")
    public ResponseEntity<?> deleteUsersByIdArray(@RequestBody List<Long> idArray){
        return new ResponseEntity<>(userService.deleteUsersByIdArray(idArray));
    }
}