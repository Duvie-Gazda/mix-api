package com.mix.api.controller.service;

import com.mix.api.model.*;
import com.mix.api.repository.*;
import jdk.net.SocketFlow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

/*
*
*       ----- GROUP USER ----
*
* # add USER GROUP
* # delete USER from GROUP
* # add USER_GROUP_ROLE to USER
* # delete USER_GROUP_ROLE from USER
* # get USERS by GROUP
*
*   ----- USER GROUP ROLE TYPE -----
*
* # create USER_GROUP_ROLE_TYPE
* # delete USER_GROUP_ROLE_TYPE
* # update USER_GROUP_ROLE_TYPE
* # get USER_GROUP_ROLE_TYPE by USER and GROUP
* # get USER_GROUP_ROLE_TYPE by id
* # get USER_GROUP_ROLE_TYPE by name
*
*
*   ----- USER GROUP ROLE -----
*
* # get USER_GROUP_ROLE by id
* # get USER_GROUP_ROLE by USER
* # get USER_GROUP_ROLE by GROUP
* # get USER_GROUP_ROLE by ROLE_TYPE
*
*   ----- USER GROUP DATA -----
*
* # create USER_GROUP_DATA
* # delete USER_GROUP_DATA
* # update USER_GROUP_DATA
* # get USER_GROUP_DATA by USER and GROUP
* # get USER_GROUP_DATA by date
* # get USER_GROUP_DATA by STATUS
* get USER_GROUP_DATA by id
* get DATA's by STATUS
* get DATA's by TYPE
* get DATA's by USER
* get DATA's by GROUP
* add USER_GROUP_DATA to GROUP
*
*   ---- DATA STATUS -----
*
* # create DATA_STATUS
* # delete DATA_STATUS
* # update DATA_STATUS
* # get DATA_STATUS by id
* # get DATA_STATUS by name
* get STATUS by DATA
* add STATUS to DATA
* delete STATUS from DATA
*
*             ------ GROUP USER DATA TYPE -----
*
*  get TYPE by
*  add TYPE to DATA
*  delete TYPE from DATA
*
*
* */

@Component
public class UserGroupService {
    @Autowired
    private UserGroupDataRepository userGroupDataRepository;

    @Autowired
    private UserGroupRoleRepository userGroupRoleRepository;

    @Autowired
    private UserGroupRoleTypeRepository userGroupRoleTypeRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DataTypeRepository dataTypeRepository;


    //  USER GROUP ROLE

    public UserGroupRole getUserGroupRoleById(Long id){
        return userGroupRoleRepository.findUserGroupRoleById(id);
    }

    public Set<UserGroupRole> getUserGroupRoleByUser(User user){
        return  userGroupRoleRepository.findUserGroupRolesByUser(user);
    }

    public Set<UserGroupRole> getUserGroupRoleByGroup (Group group){
        return  userGroupRoleRepository.findUserGroupRoleByGroup(group);
    }
    //   USER GROUP ROLE TYPE

    public void createUserGroupRoleType(UserGroupRoleType groupRoleType){
        userGroupRoleTypeRepository.save(groupRoleType);
    }
    public void deleteUserGroupRoleType(UserGroupRoleType groupRoleType){
        userGroupRoleTypeRepository.delete(groupRoleType);
    }
    public void updateUserGroupRoleType(UserGroupRoleType groupRoleType){
        userGroupRoleTypeRepository.save(groupRoleType);
    }
    public Set<UserGroupRoleType> getUserGroupRoleTypesByUserAndGroup(User user, Group group){
        Set<UserGroupRoleType> userGroupRoleTypes = new HashSet<>();
        Set<UserGroupRole> userGroupRoles = userGroupRoleRepository.findUserGroupRoleByGroupAndUser(group, user);
        for(UserGroupRole userGroupRole: userGroupRoles){
            userGroupRoleTypes.add(userGroupRole.getRoleType());
        }
        return userGroupRoleTypes;
    }

    public UserGroupRoleType getUserGroupRoleTypeById(Long id){
        return userGroupRoleTypeRepository.findUserGroupRoleTypeById(id);
    }

    public UserGroupRoleType getUserGroupRoleTypeByName(String name){
        return userGroupRoleTypeRepository.findUserGroupRoleTypeByName(name);
    }



//  GROUP USER

    public HttpStatus addUserToGroup(Group group, User user, UserGroupRoleType roleType){
        try {
            userGroupRoleRepository.save(new UserGroupRole(user, group, roleType));
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public Set<User> getUserByGroup(Group group){
        Set<User> users = new HashSet<>();
        Set<UserGroupRole> all = userGroupRoleRepository.findUserGroupRoleByGroup(group);
        for(UserGroupRole userGroupRole : all){
            users.add(userGroupRole.getUser());
        }
        return users;
    }

    public HttpStatus deleteUserFromGroup(Group group, User user){
        try {
            userGroupRoleRepository.deleteAll(userGroupRoleRepository.findUserGroupRoleByGroupAndUser(group,user));
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus addUserGroupRoleToUser(Group group, User user, UserGroupRoleType roleType){
        try {
            userGroupRoleRepository.save(new UserGroupRole(user, group, roleType));
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteUserGroupRoleFromUser(Group group, User user){
        try {
            Set<UserGroupRole> userGroups = userGroupRoleRepository.findUserGroupRoleByGroupAndUser(group, user);
            userGroupRoleRepository.deleteAll(userGroups);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

//   USER GROUP DATA

    public HttpStatus createUserGroupData(UserGroupData userGroupData){
        try{
            userGroupDataRepository.save(userGroupData);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public UserGroupData updateUserGroupData(UserGroupData userGroupData){
        userGroupDataRepository.save(userGroupData);
        return userGroupData;
    }

    public HttpStatus deleteUserGroupData(UserGroupData userGroupData){
        try{
            userGroupDataRepository.delete(userGroupData);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public Set<UserGroupData> getUserGroupDataByUserAndGroup (User user, Group group){
        return userGroupDataRepository.findUserGroupDataByUserAndGroup(user, group);
    }

    public UserGroupData getUserGroupDataByTime (LocalDateTime time){
        return  userGroupDataRepository.findUserGroupDataByTime(time);
    }

    public UserGroupData getUserGroupDataByData (Data data){
        return userGroupDataRepository.findUserGroupDataByData(data);
    }


    public UserGroupData getUserGroupDataByStatus (DataStatus status){
        return  userGroupDataRepository.findUserGroupDataByStatus(status);
    }

    public UserGroupData getUserGroupDataById(Long id){
        return userGroupDataRepository.findUserGroupDataById(id);
    }


    public Set<UserGroupRole> getUserGroupRoleByRoleType(UserGroupRoleType userGroupRoleType){
        return userGroupRoleRepository.findUserGroupRoleByRoleType(userGroupRoleType);
    }

    public UserGroupData addUserGroupDataToGroup(User user, Group group, Data data, DataType dataType, DataStatus dataStatus, LocalDateTime time){
        UserGroupData userGroupData = new UserGroupData(time, user, group, data, dataType, dataStatus);
        userGroupDataRepository.save(userGroupData);
        return userGroupData;
    }

    public Data getDataById(Long id){
        return dataRepository.findDataById(id);
    }

//  GROUP DATA TYPE

    public DataType getDataTypeById(Long id){
        return dataTypeRepository.findDataTypeById(id);
    }
}
