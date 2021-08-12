package com.mix.api.controller;

import com.mix.api.controller.service.UserService;
import com.mix.api.model.User;
import com.mix.api.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;


/*
*                      ---- USER ----
*  # create USER                              (/users/new, post)
*  # delete USER by id                        (/users/{id}, delete)
*  # update USER                              (/users, put)
*  # get all USERS                            (/users, get)
*  # get USER by nick                         (/users/nick/{nick}, get)
*  # get USER by id                           (/users/{id}, get)
*  # get USERS by USER_ROLE id                (/users/by-roles/{id}, get)
*  # get USERS by USER_ROLE name              (/users/by-roles/name/{name}, get)
*  get USERS by USER_DATA id                (/users/by-data/{id}, get)
*  get USERS by USER_DATA name              (/users/by-data/name/{name}, get)
*   == Not full ==
*  get USER (nick) by USER_ROLE id          (/users/params/nick/by-roles/{id}, get)
*  get USER (nick) by USER_ROLE name        (/users/params/nick/by-roles/{name}, get)
*  get USERS (nick) by USER_DATA id         (/users/params/nick/by-data/{id}, get)
*  get USERS (nick) by USER_DATA name       (/users/params/nick/by-data/name/{name}, get)
*  get USER (nick) by id                    (/users/params/nick/{id}, get)
*
*                     ---- USER ROLE ----
*  # create USER_ROLE                     (/users/roles/new/{name}, put)
*  # delete USER_ROLE                     (/users/roles/{id}, delete)
*  # get all USER_ROLES                   (/users/roles, get)
*  # get USER_ROLE by id                  (/users/roles/{id}, get)
*  # get USER_ROLE by name                (/users/roles/name/{name}, get)
*  get USER_ROLES by USER id            (/users/{id}/roles, get)
*  get USER_ROLES by USER nick          (/users/nick/{nick}/roles, get)
*  get USER_ROLES by USERS id array     (/users/roles, post)
*  delete USER_ROLE from USER by id's   (/users/{user_id}/roles/{role_id}, delete)
*  add USER_ROLE to USER                (/users/{user_id}/roles/{role_id}, get)
*
*
* ---- PRIVATE ----
*  convert USER to UserNickDTO
* */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

//    USER

    @PostMapping(path = "/users/new")
    public ResponseEntity<?> createUser (@RequestBody User user){
        return new ResponseEntity<>(userService.createUser(user));
    }

    @DeleteMapping(path = "/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUser(userService.getUserById(id)));
    }

    @PutMapping(path = "/users")
    public ResponseEntity<?> updateUser (@RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user));
    }

    @GetMapping(path = "/users")
    public Set<User> getAllUsers (){
        return userService.getAllUsers();
    }

    @GetMapping(path = "/users/nick/{nick}")
    public User getUserByNick(@PathVariable String nick){
        return userService.getUserByNick(nick);
    }

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable Long id){
        return userService.getUserById(id);
    }

    @GetMapping(path = "/users/by-roles/{id}")
    public Set<User> getUserByRoleId(@PathVariable Long id){
        return userService.getUsersByUserRole(userService.getUserRoleById(id));
    }

    @GetMapping(path = "/users/by-roles/name/{name}")
    public Set<User> getUserByRoleName(@PathVariable String name){
        return userService.getUsersByUserRole(userService.getUserRoleByName(name));
    }

//    Not full

//    USER ROLE

    @GetMapping(path = "/users/roles/new/{name}")
    public ResponseEntity<?> createUserRole(@PathVariable String name){
        return  new ResponseEntity<>(userService.createUserRole(name));
    }

    @DeleteMapping(path = "/users/roles/{id}")
    public ResponseEntity<?> deleteUserRoleById(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUserRole(userService.getUserRoleById(id)));
    }


    @GetMapping(path = "/users/roles")
    public List<UserRole> getAllUserRoles (){
        return userService.getAllUserRoles();
    }


    @GetMapping(path = "/users/roles/{id}")
    public UserRole getUserRoleById(@PathVariable Long id){
        return userService.getUserRoleById(id);
    }

    @GetMapping(path = "/users/roles/name/{name}")
    public UserRole getUserRoleByName(@PathVariable String name){
        return userService.getUserRoleByName(name);
    }

    @GetMapping(path = "/users/{user_id}/roles/{role_id}")
    public ResponseEntity<?> addUserRoleToUser(@PathVariable Long role_id, @PathVariable Long user_id){
        return new ResponseEntity<>(userService.addUserRoleToUser(userService.getUserRoleById(role_id),userService.getUserById(user_id)));
    }

}