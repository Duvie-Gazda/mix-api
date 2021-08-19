package com.mix.api.controller;

import com.mix.api.controller.consts.UserGroupRole;
import com.mix.api.controller.dto.FastUserDto;
import com.mix.api.controller.service.UserService;
import com.mix.api.model.*;
import com.mix.api.security.JwtProvider;
import com.mix.api.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


/*
*                      ---- USER ----
*
*  # create USER                              (/users/new, put) // all
*  # auth                                     (/users/auth, post) // all
*  # delete USER by id                        (/users/{id}, delete) // user and admin
*  # update USER                              (/users, put) // user and admin
*  # get all USERS                            (/users, get) // admin
*  # get USER by nick                         (/users/nick/{nick}, get) // auth
*  # get USER by id                           (/users/{id}, get) // auth
*  # get USERS by USER_ROLE id                (/users/by-roles/{id}, get) // auth
*  # get USERS by USER_ROLE name              (/users/by-roles/name/{name}, get) // auth
*  # get USERS by USER_DATA id                (/users/by-data/{id}, get) // auth
*  # get USERS by USER_DATA name              (/users/by-data/name/{name}, get) // auth
*  # delete USERS by USERS id Array           (/users, delete) // auth
*  # get USER'S GROUPS                        (/users/{id}/groups, get) // user and admin
*   == Not full ==
*  # get USER (nick) by USER_ROLE id          (/users/params/nick/by-roles/{id}, get) // auth
*  # get USER (nick) by USER_ROLE name        (/users/params/nick/by-roles/{name}, get) // auth
*  # get USERS (nick) by USER_DATA id         (/users/params/nick/by-data/{id}, get) // auth
*  # get USERS (nick) by USER_DATA name       (/users/params/nick/by-data/name/{name}, get) // auth
*  # get USER (nick) by id                    (/users/params/nick/{id}, get) // auth
*
*                     ---- USER ROLE ----
*
*  # create USER_ROLE                     (/users/roles/new/{name}, put) // admin
*  # delete USER_ROLE by id               (/users/roles/{id}, delete) // admin
*  # get all USER_ROLES                   (/users/roles, get) // admin
*  # get USER_ROLE by id                  (/users/roles/{id}, get) // admin
*  # get USER_ROLE by name                (/users/roles/name/{name}, get) // admin
*  # get USER_ROLES by USER id            (/users/{id}/roles, get) // admin
*  # get USER_ROLES by USER nick          (/users/nick/{nick}/roles, get) // admin
*  # get USER_ROLES by USERS id array     (/users/roles, post) // admin
*  # delete USER_ROLE from USER by id's   (/users/{user_id}/roles/{role_id}, delete) // admin
*  # add USER_ROLE to USER                (/users/{user_id}/roles/{role_id}, put) // admin
*
*                   ----- USER DATA ----
*
*  # create USER_DATA                      (/users/data/new, put) // user and admin
*  # create USER_DATA by name              (/users/data/new/{name}, put) // user and admin
*  # delete USER_DATA                      (/users/data/{id}, delete) // user and admin
*  # update USER_DATA                      (/users/data, put) // user and admin
*  # get USER_DATA by id                   (/users/data/{id}, get)
*  # get USER_DATA by name                 (/users/data/name/{name}, get)
*  # get all USER_DATA by USER id          (/users/{id}/data, get)
*  # get all USER_DATA by USERs name       (/users/name/{name}/data, get)
*  # get USER_DATAs by USER_DATA_TYPE id   (/users/by-data-type/{id}, get)
*  # get USER_DATAs by USER_DATA_TYPE name (/users/by-data-type/name/{name}, get)
*  # add USER_DATA to USER                 (/users/{user_id}/data/{data_id}/type/{type_id}, put)
*  # smart add USER_DATA to USER           (/users/{user_id}/data/name/{data_name}/type/{type_id}, put)
*  # delete USER_DATA from USER            (/users/{user_id}/data/{data_id}/type/{type_id}, delete)
*
*               ----- USER DATA TYPE ----
*
*  # create USER_DATA_TYPE                   (/users/data/types/new, put)
*  # update USER_DATA_TYPE                   (/users/data/types, put)
*  # delete USER_DATA_TYPE                   (/users/data/types/{id}, delete)
*  # get USER_DATA_TYPE by id                (/users/data/types/{id}, get)
*  # get USER_DATA_TYPE by name              (/users/data/types/{name}, get)
*  # get USER_DATA_TYPE by USER_DATA id      (/users/data/{id}/types/, get)
*  # get USER_DATA_TYPE by USER_DATA name    (/users/data/name/{name}/types/, get)
*
* */
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

//    USER

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
        if(hasPermissions(SecurityContextHolder.getContext(), user)){
            userService.deleteUser(user);
        }
    }

    @PutMapping(path = "/users")
    public void updateUser (@RequestBody User user){
        if(hasPermissions(SecurityContextHolder.getContext(), user)){
            userService.updateUser(user);
        }
    }

    @GetMapping(path = "/users")
    public Set<User> getAllUsers (){
        if(isAdmin(SecurityContextHolder.getContext())) {
            return userService.getAllUsers();
        }
        return null;
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

    @GetMapping(path = "/users/by-data/{id}")
    public Set<User> getUsersByUserDataId(@PathVariable Long id){
        return userService.getUsersByUserData(userService.getUserDataById(id));
    }

    @GetMapping(path = "/users/by-data/name/{name}")
    public Set<User> getUserByUserDataName(@PathVariable String name){
        return userService.getUsersByUserData(userService.getUserDataByName(name));
    }

    @DeleteMapping(path = "/users")
    public void deleteUsersByUserIdArray(@RequestBody List<Long> ids){
        if(isAdmin(SecurityContextHolder.getContext())){
            userService.deleteUsersByIdArray(ids);
        }
    }

    @GetMapping(path = "/users/{id}/groups")
    public Set<Group> getUsersGroups(@PathVariable Long id){
        return userService.getGroupsByUser(userService.getUserById(id));
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

    @PutMapping(path = "/users/roles/new/{name}")
    public void createUserRole(@PathVariable String name){
        if(isAdmin(SecurityContextHolder.getContext())) {
            userService.createUserRole(name);
        }
    }

    @DeleteMapping(path = "/users/roles/{id}")
    public void deleteUserRoleById(@PathVariable Long id){
        if(isAdmin(SecurityContextHolder.getContext())) {
            userService.deleteUserRole(userService.getUserRoleById(id));
        }
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
        if(isAdmin(SecurityContextHolder.getContext())) {
            userService.deleteUserRoleFromUser(userService.getUserRoleById(role_id), userService.getUserById(user_id));
        }
    }


//  USER DATA


    @PutMapping(path = "/users/{user_id}/data/name/{data_name}/types/name/{type_name}")
    public void smartCreateUserData(@PathVariable String data_name, @PathVariable String type_name, @PathVariable Long user_id){
        if(hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))){
            UserData userData = new UserData(data_name);
            userService.createUserData(userData);
            UserDataType userDataType = userService.getUserDataTypeByName(type_name);
            if(userDataType == null){
                userDataType = userService.createUserDataType(new UserDataType(type_name));
            }
            userService.addUserDataToUser(
                    userService.getUserById(user_id),
                    userService.getUserDataByName(userData.getName()),
                    userService.getUserDataTypeById(userDataType.getId())
            );
        }
    }

    @PutMapping(path = "/users/{user_id}/data/name/{data_name}/types/{type_id}")
    public void smartCreateUserData(@PathVariable String data_name, @PathVariable Long type_id, @PathVariable Long user_id){
        if(hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))){
            UserData userData = new UserData(data_name);
            userService.createUserData(userData);
            userService.addUserDataToUser(
                    userService.getUserById(user_id),
                    userService.getUserDataByName(userData.getName()),
                    userService.getUserDataTypeById(type_id)
            );
        }
    }

    @DeleteMapping(path = "/users/{user_id}/data/{data_id}")
    public void deleteUserData(@PathVariable Long data_id, @PathVariable Long user_id) {
        if (hasPermissions(SecurityContextHolder.getContext(), userService.getUserById(user_id))) {
            userService.deleteUserDataFromUser(
                    userService.getUserById(user_id),
                    userService.getUserDataById(data_id)
            );
        }
    }








//    USER DATA

    @PutMapping(path = "/users/{}/data/new")
    public void  createUserData(@RequestBody UserData userData){
        userService.createUserData(userData);
    }

    @PutMapping(path = "/users/data/new/{name}")
    public void createUserDataByName(@PathVariable String name){
        userService.createUserData(
                new UserData(name)
        );
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
        UserData data = new UserData(data_name);
        userService.createUserData(data);
        userService.addUserDataToUser(
            userService.getUserById(user_id),
            data,
            userService.getUserDataTypeById(type_id)
        );
    }

    @PutMapping(path = "/users/{user_id}/data/{data_id}/type/{type_id}")
    public void addUserDataToUser(@PathVariable Long data_id, @PathVariable Long user_id, @PathVariable Long type_id){
        userService.addUserDataToUser(
                userService.getUserById(user_id),
                userService.getUserDataById(data_id),
                userService.getUserDataTypeById(type_id)
        );
    }

    @DeleteMapping(path = "/users/{user_id}/data/{data_id}}")
    public void deleteUserDataFromUser(@PathVariable Long data_id, @PathVariable Long user_id){
        userService.deleteUserDataFromUser(
                userService.getUserById(user_id),
                userService.getUserDataById(data_id)
        );
    }

//    USER DATA TYPE

    @PutMapping(path = "/users/data/types/new")
    public void createUserDataType(@RequestBody UserDataType userDataType){
        userService.createUserDataType(userDataType);
    }

    @PutMapping(path = "/users/data/types")
    public void updateUserDataType(@RequestBody UserDataType userDataType){
        userService.updateUserDataType(userDataType);
    }

    @DeleteMapping(path = "/users/data/types/{id}")
    public void deleteUserDataType(@PathVariable Long id){
        userService.deleteUserDataType(userService.getUserDataTypeById(id));
    }

    @GetMapping(path = "/users/data/types/{id}")
    public UserDataType getUserDataTypeById(@PathVariable Long id){
        return userService.getUserDataTypeById(id);
    }

    @GetMapping(path = "/users/data/types/{name}")
    public UserDataType getUserDataTypeByName(@PathVariable String name){
        return userService.getUserDataTypeByName(name);
    }

    @GetMapping(path = "/users/data/{id}/types")
    public Set<UserDataType> getUserDataTypeByUserDataId(@PathVariable Long id){
        return userService.getUserDataTypeByUserData(userService.getUserDataById(id));
    }

    @GetMapping(path = "/users/data/name/{name}/types")
    public Set<UserDataType> getUserDataTypeByUserDataName(@PathVariable String name){
        return userService.getUserDataTypeByUserData(userService.getUserDataByName(name));
    }

    private boolean hasPermissions(SecurityContext securityContext, User userEvent){
        UserDetails currentUser = (UserDetails) securityContext.getAuthentication().getCredentials();
        User user = userService.getUserByNick(currentUser.getUsername());
        return isAdmin(securityContext) || userEvent.getId().equals(user.getId());
    }

    private boolean isAdmin(SecurityContext securityContext){
        UserDetails currentUser = (UserDetails) securityContext.getAuthentication().getCredentials();
        User user = userService.getUserByNick(currentUser.getUsername());
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(
                userService.getUserRoleById(UserGroupRole.ADMIN)
        );
        return user.getUserRoles().containsAll(userRoles);
    }
}