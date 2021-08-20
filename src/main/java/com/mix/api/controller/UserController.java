package com.mix.api.controller;

import com.mix.api.controller.dto.FastUserDto;
import com.mix.api.controller.helper.PermissionHelper;
import com.mix.api.controller.service.UserService;
import com.mix.api.model.*;
import com.mix.api.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private PermissionHelper permissionHelper;

//    USER

//    @PutMapping(path = "/users/roles/name/{role_name}")
//    public void smartCrateUserWithRoleName(@PathVariable String role_name, @RequestBody User user){
//        userService.createUser(user);
//        UserRole userRole = new UserRole(role_name);
//        userService.createUserRole(userRole);
//        userService.addUserRoleToUser(
//                userRole,
//                user
//        );
//    }

    @PutMapping(path = "/uses/roles/{role_id}")
    public void smartCreateUserWithRoleId(@PathVariable Long role_id, @RequestBody User user){
        userService.createUser(user);
        userService.addUserRoleToUser(
                userService.getUserRoleById(role_id),
                user
        );
    }

    @PutMapping(path = "/users/new")
    public void createUser (@RequestBody User user){
        userService.createUser(user);
    }

    @PostMapping(path = "/users/auth")
    public String authUser(@RequestBody User user){
        User dbUser = userService.getUserByNick(user.getNick());
        if(passwordEncoder.matches(user.getPass(), dbUser.getPass())){
            return jwtProvider.generateToken(dbUser.getId(), dbUser.getNick());
        }
        return "";
    }

    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable Long id){
        User user = userService.getUserById(id);
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), user)){
            userService.deleteUser(user);
        }
    }

    @PutMapping(path = "/users")
    public void updateUser (@RequestBody User user){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), user)){
            userService.updateUser(user);
        }
    }

    @GetMapping(path = "/users")
    public Set<User> getAllUsers (){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getAllUsers();
        }
        return null;
    }

    @GetMapping(path = "/users/by-data/{id}")
    public Set<User> getUsersByUserDataId(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUsersByUserData(userService.getUserDataById(id));
        }
        return null;
    }

    @GetMapping(path = "/users/by-data/name/{name}")
    public Set<User> getUserByUserDataName(@PathVariable String name){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUsersByUserData(userService.getUserDataByName(name));
        }
        return null;
    }

    @GetMapping(path = "/users/nick/{nick}")
    public User getUserByNick(@PathVariable String nick){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUserByNick(nick);
        }
        return null;
    }

    @GetMapping(path = "/users/{id}")
    public User getUserById(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())){
            return userService.getUserById(id);
        }
        return null;
    }

    @GetMapping(path = "/users/by-roles/{id}")
    public Set<User> getUserByRoleId(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUsersByUserRole(userService.getUserRoleById(id));
        }
        return null;
    }

    @GetMapping(path = "/users/by-roles/name/{name}")
    public Set<User> getUserByRoleName(@PathVariable String name){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())){
            return userService.getUsersByUserRole(userService.getUserRoleByName(name));
        }
        return null;
    }

//    Not full

    @GetMapping (path = "/users/params/nick/by-roles/{id}")
    public FastUserDto getFastUserByRoleId(@PathVariable Long id){
        return new FastUserDto(userService.getUserById(id));
    }

    @GetMapping (path = "/users/params/nick/by-roles/nick/{nick}")
    public FastUserDto getFastUserByRoleName(@PathVariable String nick){
        return new FastUserDto(userService.getUserByNick(nick));
    }

    @GetMapping(path = "/users/params/nick/by-data/{id}")
    public Set<FastUserDto> getFastUsersByUserDataId(@PathVariable Long id){
        Set<FastUserDto> fastUserDtos = new HashSet<>();
        Set<User> users = userService.getUsersByUserData(userService.getUserDataById(id));
        for (User user: users){
            fastUserDtos.add(new FastUserDto(user));
        }
        return fastUserDtos;
    }

    @GetMapping(path = "/users/params/nick/by-data/{name}")
    public Set<FastUserDto> getFastUsersByUserDataName (@PathVariable String name){
        Set<FastUserDto> fastUserDtos = new HashSet<>();
        Set<User> users = userService.getUsersByUserData(userService.getUserDataByName(name));
        for (User user: users){
            fastUserDtos.add(new FastUserDto(user));
        }
        return fastUserDtos;
    }

    @GetMapping(path = "/users/params/nick/{id}")
    public FastUserDto getFastUserById(@PathVariable Long id){
        return new FastUserDto(userService.getUserById(id));
    }


//    USER ROLE

    @PutMapping(path = "users/{user_id}/roles/{role_name}")
    public void smartCreateUserRole(@PathVariable String role_name, @PathVariable Long user_id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            UserRole userRole = new UserRole(role_name);
            userService.createUserRole(userRole);
            userService.addUserRoleToUser(
                    userRole,
                    userService.getUserById(user_id)
            );
        }
    }

    @PutMapping(path = "/users/roles/{role_name}")
    public void smartCreateRolesByName(@PathVariable String role_name){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            userService.createUserRole(new UserRole(role_name));
        }
    }

    @GetMapping(path = "/users/roles")
    public List<UserRole> getAllUserRoles (){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getAllUserRoles();
        }
        return null;
    }

    @GetMapping(path = "/users/roles/{id}")
    public UserRole getUserRoleById(@PathVariable Long id){
        return userService.getUserRoleById(id);
    }

    @GetMapping(path = "/users/roles/name/{name}")
    public UserRole getUserRoleByName(@PathVariable String name){
        return userService.getUserRoleByName(name);
    }

    @PutMapping(path = "/users/{user_id}/roles/{role_id}")
    public void addUserRoleToUser(@PathVariable Long role_id, @PathVariable Long user_id){
        userService.addUserRoleToUser(userService.getUserRoleById(role_id),userService.getUserById(user_id));
    }

    @GetMapping(path = "/users/{id}/roles")
    public Set<UserRole> getUserRolesByUserId(@PathVariable Long id){
        return userService.getUserRoleByUser(userService.getUserById(id));
    }

    @PostMapping(path = "/users/roles")
    public Set<UserRole> getUserRolesByUserIdsArray(@RequestBody List<Long> idList){
        return userService.getUserRoleByUsers(userService.getUsersByIdsArray(idList));
    }

    @GetMapping(path = "/users/nick/{nick}/roles")
    public Set<UserRole> getUserRolesByUserNick(@PathVariable String nick){
        return userService.getUserRoleByUser(userService.getUserByNick(nick));
    }

    @DeleteMapping(path = "/users/{user_id}/roles/{role_id}")
    public void deleteUserRoleFromUser(@PathVariable Long role_id, @PathVariable Long user_id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            userService.deleteUserRoleFromUser(userService.getUserRoleById(role_id), userService.getUserById(user_id));
        }
    }



//    USER DATA

    @PutMapping(path = "/users/{user_id}/data/{data_name}/type/name/{type_name}")
    public void smartCreateUserData(@PathVariable String data_name, @PathVariable String type_name, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))){
            UserData userData = new UserData(data_name);
            userService.createUserData(userData);
            UserDataType userDataType = new UserDataType(type_name);
            userService.createUserDataType(userDataType);
            userService.addUserDataToUser(
                    userService.getUserById(user_id),
                    userData,
                    userDataType
            );
        }

    }

    @PutMapping(path = "/users/{user_id}/data/{data_name}/type/{type_id}")
    public void smartCreateUserData(@PathVariable String data_name, @PathVariable Long type_id, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))){
            UserData userData = new UserData(data_name);
            userService.createUserData(userData);
            userService.addUserDataToUser(
                    userService.getUserById(user_id),
                    userData,
                    userService.getUserDataTypeById(type_id)
            );
        }
    }

    @PutMapping(path = "/users/{user_id}/data/{data_id}/type/{type_id}")
    public void connectUserDataToUser(@PathVariable Long data_id, @PathVariable Long type_id, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))){
            userService.addUserDataToUser(
                    userService.getUserById(user_id),
                    userService.getUserDataById(data_id),
                    userService.getUserDataTypeById(type_id)
            );
        }
    }

    @PutMapping(path = "/users/{user_id}/data/{data_id}/type/name/{type_name}")
    public void connectUserDataToUser(@PathVariable Long data_id, @PathVariable String type_name, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))){
            userService.addUserDataToUser(
                    userService.getUserById(user_id),
                    userService.getUserDataById(data_id),
                    userService.getUserDataTypeByName(type_name)
            );
        }
    }


    @DeleteMapping(path = "/users/data/{id}")
    public void deleteUserData(@PathVariable Long id){
        userService.deleteUserData(userService.getUserDataById(id));
    }

    @PutMapping(path = "/users/data")
    public void updateUserData(@RequestBody UserData userData){
        userService.updateUserData(userData);
    }

    @GetMapping(path = "/users/data/{id}")
    public UserData getUserDataById(@PathVariable Long id){
        return userService.getUserDataById(id);
    }

    @GetMapping(path = "/users/data/name/{name}")
    public UserData getUserDataByName(@PathVariable String name){
        return userService.getUserDataByName(name);
    }

    @GetMapping(path = "/users/{id}/data")
    public Set<UserData> getUserDataByUserId(@PathVariable Long id){
        return userService.getUserDataByUser(userService.getUserById(id));
    }

    @GetMapping(path = "/users/nick/{nick}/data")
    public Set<UserData> getUserDataByUserName(@PathVariable String nick){
        return  userService.getUserDataByUser(userService.getUserByNick(nick));
    }

    @GetMapping(path = "/users/by-data-type/{id}")
    public Set<UserData> getUserDataByUserDataTypeId(@PathVariable Long id){
        return userService.getUserDataByUserDataType(userService.getUserDataTypeById(id));
    }

    @GetMapping(path = "/users/by-data-type/name/{name}")
    public Set<UserData> getUserDataByUserDataTypeName(@PathVariable String name){
        return userService.getUserDataByUserDataType(userService.getUserDataTypeByName(name));
    }

    @PutMapping(path = "/users/{user_id}/data/name/{data_name}/type/{type_id}")
    public void smartAddUserDataToUser(@PathVariable String data_name, @PathVariable Long type_id, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))){
            UserData data = new UserData(data_name);
            userService.createUserData(data);
            userService.addUserDataToUser(
                    userService.getUserById(user_id),
                    data,
                    userService.getUserDataTypeById(type_id)
            );
        }
    }


    @DeleteMapping(path = "/users/{user_id}/data/{data_id}}")
    public void deleteUserDataFromUser(@PathVariable Long data_id, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))) {
            userService.deleteUserDataFromUser(
                    userService.getUserById(user_id),
                    userService.getUserDataById(data_id)
            );
        }
    }



//    USER DATA TYPE

    @PutMapping(path = "/users/data/types/new")
    public void createUserDataType(@RequestBody UserDataType userDataType){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())){
            userService.createUserDataType(userDataType);
        }
    }

    @PutMapping(path = "/users/data/types")
    public void updateUserDataType(@RequestBody UserDataType userDataType){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            userService.updateUserDataType(userDataType);
        }
    }

    @DeleteMapping(path = "/users/data/types/{id}")
    public void deleteUserDataType(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            userService.deleteUserDataType(userService.getUserDataTypeById(id));
        }
    }

    @GetMapping(path = "/users/data/types/{id}")
    public UserDataType getUserDataTypeById(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUserDataTypeById(id);
        }
        return null;
    }

    @GetMapping(path = "/users/data/types/{name}")
    public UserDataType getUserDataTypeByName(@PathVariable String name){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUserDataTypeByName(name);
        }
        return  null;
    }

    @GetMapping(path = "/users/data/{id}/types")
    public Set<UserDataType> getUserDataTypeByUserDataId(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUserDataTypeByUserData(userService.getUserDataById(id));
        }
        return  null;
    }

    @GetMapping(path = "/users/data/name/{name}/types")
    public Set<UserDataType> getUserDataTypeByUserDataName(@PathVariable String name){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return userService.getUserDataTypeByUserData(userService.getUserDataByName(name));
        }
        return null;
    }

}