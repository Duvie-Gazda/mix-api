package com.mix.api.controller.service;

import com.mix.api.model.*;
import com.mix.api.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
   import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/*
 *   ---- USER -----
 *  # create USER
 *  # delete USER
 *  # update USER
 *  # delete USERS by ID array
 *  get All USERS
 *  # get USERS by USER_ROLE
 *  # get USERS by USER_DATA
 *  # get USER by USER_DATA_TYPE
 *  # get USER by nick
 *  # get USER by id
 *
 *
 *   ----- USER ROLE -----
 *  # get USER_ROLE by (name, id)
 *  # create USER_ROLE
 *  # delete USER_ROLE
 *  # update USER_ROLE
 *  # add USER_ROLE to USER
 *  # delete USER_ROLE from USER
 *  # get USER_ROLES by USER
 *  # get USER_ROLES by USERS
 * get all USER_ROLES
 *
 *   ----- USER DATA -----
 *  # get USER_DATA by (name,id)
 *  # create USER_DATA
 *  # delete USER_DATA
 *  # update USER_DATA
 *  # add USER_DATA to USER
 *  # delete USER_DATA from USER
 *  # get USER_DATA by USER_DATA_TYPE
 *
 *
 * */
@Component
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private UserDataDataTypeRepository dataDataTypeRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private UserDataTypeRepository userDataTypeRepository;


//    USER

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
        for (Long id: idList) {
            try{
                userRepository.delete(userRepository.findUserById(id));
            }catch (Throwable throwable){
                return  HttpStatus.BAD_GATEWAY;
            }
        }
        return HttpStatus.OK;
    }

    public Set<User> getAllUsers(){ return userRepository.findAll();}

    public Set<User> getUsersByUserRole(UserRole userRole){ return userRepository.findByUserRoles_Id(userRole.getId());}

    public Set<User> getUsersByUserData(UserData userData){
        Set<User> users = new HashSet<User>();
        Set<UserDataDataType> dataDataTypes = dataDataTypeRepository.findUserDataDataTypesByData(userDataRepository.findUserDataById(userData.getId()));
        for (UserDataDataType dataDataType: dataDataTypes) {
            users.add(dataDataType.getUser());
        }
        return users;
    }

    public Set<User> getUsersByUserDataType(UserDataType dataType){
        Set<User> users = new HashSet<>();
        Set<UserDataDataType> dataDataTypes = dataDataTypeRepository.findUserDataDataTypesByDataType(userDataTypeRepository.findUserDataTypeById(dataType.getId()));
        for (UserDataDataType dataDataType: dataDataTypes) {
            users.add(dataDataType.getUser());
        }
        return users;
    }

    public User getUserByNick(String nick){
        return userRepository.findUserByNick(nick);
    }

    public User getUserById (Long id){
        return userRepository.findUserById(id);
    }


//    USER ROLE

    public UserRole getUserRoleByName (String name){ return  userRoleRepository.findUserRoleByName(name);}

    public UserRole getUserRoleById (Long id){ return userRoleRepository.findUserRoleById(id);}

    public HttpStatus createUserRole(String roleName){
        try {
            UserRole userRole = new UserRole(roleName);
            userRoleRepository.save(userRole);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }


    public HttpStatus deleteUserRole(UserRole userRole){
        try {
            userRoleRepository.delete(userRole);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus addUserRoleToUser(UserRole userRole, User user){
        try {
            user.getUserRoles().add(userRole);
            userRepository.save(user);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUserRoleFromUser(UserRole userRole, User user){
        try {
            user.getUserRoles().remove(userRole);
            userRepository.save(user);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public Set<UserRole> getUserRoleByUser(User user){ return user.getUserRoles();}

    public Set<UserRole> getUserRoleByUsers(Set<User> users){
        Set<UserRole> userRoles = new HashSet<UserRole>();
        for (User user: users) {
            userRoles.addAll(user.getUserRoles());
        }
        return userRoles;
    }

    public List<UserRole> getAllUserRoles(){ return userRoleRepository.findAll();}



//    USER DATA

    public UserData getUserDataByName(String name) {return userDataRepository.findUserDataByName(name);}

    public UserData getUserDataById(Long id) {return  userDataRepository.findUserDataById(id);}

    public HttpStatus createUserData(String dataName){
        try{
            UserData userData = new UserData(dataName);
            userDataRepository.save(userData);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus updateUserData(UserData userData){
        try {
            userDataRepository.save(userData);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUserData(UserData userData){
        try {
            userDataRepository.delete(userData);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus addUserDataToUser(User user, UserData data, UserDataType dataType){
        try {
            UserDataDataType userDataDataType = new UserDataDataType(user,data,dataType);
            dataDataTypeRepository.save(userDataDataType);
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUserDataFromUser(User user, UserData data, UserDataType dataType){
        try {
            dataDataTypeRepository.delete(dataDataTypeRepository.findUserDataDataTypeByUserAndDataAndDataType(user,data,dataType));
        } catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public Set<UserData> getUserDataByUserDataType (UserDataType userDataType){
        Set<UserData> userData = new HashSet<>();
        Set<UserDataDataType> dataDataTypes = dataDataTypeRepository.findUserDataDataTypesByDataType(userDataTypeRepository.findUserDataTypeById(userDataType.getId()));
        for (UserDataDataType dataDataType: dataDataTypes) {
            userData.add(dataDataType.getData());
        }
        return userData;
    }

}
