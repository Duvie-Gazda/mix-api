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

//    PUT

    @PutMapping(path = "/users/{user_id}/roles/{id}")
    public ResponseEntity<?> setRoleToUserByRoleId(@PathVariable Long id, @PathVariable Long user_id){
        return new ResponseEntity<>(userService.setRoleToUserByRoleId(userService.getUserById(user_id), id));
    }

    @PutMapping(path = "/users/{user_id}/roles/{name}")
    public ResponseEntity<?> setRoleToUserByRoleName(@PathVariable Long user_id, @PathVariable String name){
        return new ResponseEntity<>(userService.setRoleToUserByRoleName(userService.getUserById(user_id), name));
    }

    @PutMapping(path = "users/{user_id}/roles/{idToDelete}/{idToAdd}")
    public ResponseEntity<?> changeRoleToAnotherByIds(@PathVariable Long idToDelete, @PathVariable Long idToAdd, @PathVariable Long user_id){
        return new ResponseEntity<>(userService.changeUsersRoleToAnotherByIds(userService.getUserById(user_id),idToDelete, idToAdd));
    }

    @PutMapping(path = "users/{user_id}/roles/{nameToDelete}/{nameToAdd}")
    public ResponseEntity<?> changeRoleToAnotherByNames(@PathVariable Long user_id, @PathVariable String nameToAdd, @PathVariable String nameToDelete){
        return new ResponseEntity<>(userService.changeUsersRoleToAnotherByNames(userService.getUserById(user_id),nameToDelete, nameToAdd));
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

    @DeleteMapping(path = "/users/{user_id}/roles/{role_id}")
    public ResponseEntity<?> deleteRoleByRoleId(@PathVariable Long role_id, @PathVariable Long user_id){
        return new ResponseEntity<>(userService.deleteUserRoleByRoleId(userService.getUserById(user_id), role_id));
    }

    @DeleteMapping(path = "/users/{user_id}/roles/{role_name}")
    public ResponseEntity<?> deleteRoleByRoleName(@PathVariable String role_name, @PathVariable Long user_id){
        return new ResponseEntity<>(userService.deleteUserRoleByRoleName(userService.getUserById(user_id), role_name));
    }

}