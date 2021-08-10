package com.mix.api.controller.service;

import com.mix.api.model.*;
import com.mix.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
   import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserDataTypeRepository userDataTypeRepository;

    public HttpStatus createUser (User user){
        try{
            userRepository.save(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUser (User user){
        try{
            userRepository.delete(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    public HttpStatus updateUser (User user){
        try{
            userRepository.save(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

//    Delete From User

    public HttpStatus deleteUserRoleByRoleId(User user, Long role_id){
        try{
            user.getUserRoles().remove(userRoleRepository.findUserRoleById(role_id));
            userRepository.save(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUserRoleByRoleName(User user, String role_name){
        try{
            user.getUserRoles().remove(userRoleRepository.findUserRoleByName(role_name));
            userRepository.save(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_REQUEST;
        }
        return HttpStatus.OK;
    }

//    Get From User

    public Set<UserData> getUserDataByDataTypeId(User user, Long dataTypeId){
        Set<UserDataType> user_data_types = userDataTypeRepository.findUserDataTypesById(dataTypeId);
        return userDataRepository.findUserDataByUserDataTypesAndUsers(user_data_types, (Set<User>) user);
    }

    public Set<UserData> getUserDataByDataTypeName(User user, String dataTypeName){
        Set<UserDataType> user_data_types = userDataTypeRepository.findUserDataTypesByName(dataTypeName);
        return userDataRepository.findUserDataByUserDataTypesAndUsers(user_data_types, (Set<User>) user);
    }

//    GET User By

    public Set<User> getUsersByRoleName(String roleName){
        UserRole userRole = userRoleRepository.findUserRoleByName(roleName);
        return userRole.getUsers();
    }

    public Set<User> getUserByRoleId(Long roleId){
        UserRole userRole = userRoleRepository.findUserRoleById(roleId);
        return userRole.getUsers();
    }

    public Set<User> getUserByUserDataName(String userDataName){
        UserData userData = userDataRepository.findUserDataByName(userDataName);
        return userData.getUsers();
    }

    public User getUserByNick(String nick){
        return userRepository.findUserByNick(nick);
    }

    public User getUserById (Long id){
        return userRepository.findUserById(id);
    }
}
