package com.mix.api.controller.service;

import com.mix.api.model.*;
import com.mix.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
   import org.springframework.stereotype.Component;

import java.util.List;
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

//    User CRUD

    public HttpStatus createUser (User user){
        try{
            userRepository.save(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUser (User user){
        try{
            userRepository.delete(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus updateUser (User user){
        try{
            userRepository.save(user);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUsersByIdArray (List<Long> idList){
        for (Long id:
             idList) {
            try{
                userRepository.delete(userRepository.findUserById(id));
            }catch (Throwable throwable){
                return  HttpStatus.BAD_GATEWAY;
            }
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

//

//    GET User By

    public Set<User> getUsersByRoleName(String roleName){
        UserRole userRole = userRoleRepository.findUserRoleByName(roleName);
        return userRole.getUsers();
    }

    public Set<User> getUsersByRoleId(Long roleId){
        UserRole userRole = userRoleRepository.findUserRoleById(roleId);
        return userRole.getUsers();
    }

    public Set<User> getUsersByUserDataName(String userDataName){
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
