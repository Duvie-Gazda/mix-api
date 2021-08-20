package com.mix.api.controller;

import com.mix.api.controller.consts.DataTypeConst;
import com.mix.api.controller.consts.UserGroupRoleConst;
import com.mix.api.controller.dto.UserGroupDataConnectDto;
import com.mix.api.controller.service.DataService;
import com.mix.api.controller.service.GroupService;
import com.mix.api.controller.service.UserGroupService;
import com.mix.api.controller.service.UserService;
import com.mix.api.model.*;
import com.mix.api.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.Set;

/*
*
*                            ----- USER GROUP DATA -----
*
*   add USER GROUP DATA                  (/add/groups/users/data/type, client: topic/groups/)
*   delete USER GROUP DATA from DATA     (/delete/groups/users/data/)
*
* */

@Controller
public class GroupMessageController {
    @Autowired
    private UserService userService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private DataService dataService;
    @Autowired
    private UserGroupService groupUserService;

    @MessageMapping("/add/groups/{group_id}/users/{user_id}/data/name/{data_name}/type/{type_id}/status/{status_id}")
    @SendTo("/topic/groups/{group_id}/users/data")
    public UserGroupData addMessage(@DestinationVariable Long group_id, @DestinationVariable Long user_id, @DestinationVariable String data_name, @DestinationVariable Long type_id, @DestinationVariable Long status_id){
        if(hasPermissionsToAddMessage(SecurityContextHolder.getContext(), groupService.getGroupById(group_id))){
            Data data = new Data(data_name);
            dataService.createData(data);
            return groupUserService.addUserGroupDataToGroup(
                    userService.getUserById(user_id),
                    groupService.getGroupById(group_id),
                    data,
                    dataService.getDataTypeById(type_id),
                    dataService.getDataStatusById(status_id),
                    LocalDateTime.now()
            );
        }
        return null;
    }

    @MessageMapping("/delete/groups/{group_id}/data/{data_id}")
    @SendTo("/topic/groups/{group_id}/users/data")
    public UserGroupData deleteMessage(@DestinationVariable Long group_id, @DestinationVariable Long data_id){
        if(hasPermissionsToAddMessage(SecurityContextHolder.getContext(), groupService.getGroupById(group_id))){
            UserGroupData  userGroupData = groupUserService.getUserGroupDataByData(
                    groupUserService.getDataById(data_id)
            );
            userGroupData.setDataType(
                    groupUserService.getDataTypeById(DataTypeConst.DELETED)
            );
            return groupUserService.updateUserGroupData(userGroupData);
        }
        return null;
    }

    @MessageMapping("/delete/groups/{group_id}/users/{user_id}")
    @SendTo("/topic/users/{user_id}/groups")
    public Group deleteUserFromGroup(@DestinationVariable Long group_id, @DestinationVariable Long user_id){
        if(hasPersmissionsToUserGroupWork(SecurityContextHolder.getContext(),userService.getUserById(user_id), groupService.getGroupById(group_id))){
            groupUserService.deleteUserFromGroup(
                    groupService.getGroupById(group_id),
                    userService.getUserById(user_id)
            );
            return groupService.getGroupById(group_id);
        }
        return null;
    }

    @MessageMapping("/add/groups/{group_id}/users/{user_id}")
    @SendTo("/topic/users/{user_id}/groups")
    public Group addUserToGroup(@DestinationVariable Long group_id, @DestinationVariable Long user_id){
        if(hasPermissionsToAddUser(SecurityContextHolder.getContext(),userService.getUserById(user_id), groupService.getGroupById(group_id))){
            groupUserService.deleteUserFromGroup(
                    groupService.getGroupById(group_id),
                    userService.getUserById(user_id)
            );
            return groupService.getGroupById(group_id);
        }
        return null;
    }

    private boolean hasPermissionsToAddUser(SecurityContext securityContext, User user,Group group){
        User currentUser = userService.getUserByNick(
                ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername()
        );
        Set<User> users = groupUserService.getUserByGroup(group);
        if(users.contains(currentUser)){
            Set<UserGroupRole> userGroupRoles = groupUserService.getUserGroupRoleByUser(userService.getUserByNick(currentUser.getNick()));
            for (UserGroupRole userGroupRole: userGroupRoles){
                if (userGroupRole.getRoleType().getId().equals(UserGroupRoleConst.ADMIN)) {return  true;}
                if (userGroupRole.getRoleType().getId().equals(UserGroupRoleConst.MODERATOR)) {return true;}
            }
            return false;
        }
        return false;
    }

    private boolean hasPersmissionsToUserGroupWork(SecurityContext securityContext, User user,Group group){
        User currentUser = userService.getUserByNick(
                ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername()
        );
        Set<User> users = groupUserService.getUserByGroup(group);
        if(users.contains(currentUser)){
            Set<UserGroupRole> userGroupRoles = groupUserService.getUserGroupRoleByUser(userService.getUserByNick(currentUser.getNick()));
            for (UserGroupRole userGroupRole: userGroupRoles){
                if (userGroupRole.getRoleType().getId().equals(UserGroupRoleConst.ADMIN)) {return  true;}
                if (userGroupRole.getRoleType().getId().equals(UserGroupRoleConst.MODERATOR)) {return true;}
                if (user.getId().equals(currentUser.getId())) { return true;}
            }
            return false;
        }
        return false;
    }

    private boolean hasPermissionsToAddMessage(SecurityContext securityContext, Group group){
        User currentUser = userService.getUserByNick(
                ((UserDetails) securityContext.getAuthentication().getPrincipal()).getUsername()
        );
        Set<User> users = groupUserService.getUserByGroup(group);
        if(users.contains(currentUser)){
            Set<UserGroupRole> userGroupRoles = groupUserService.getUserGroupRoleByUser(userService.getUserByNick(currentUser.getNick()));
            for (UserGroupRole userGroupRole: userGroupRoles){
                if (userGroupRole.getRoleType().getId().equals(UserGroupRoleConst.ADMIN)) {return  true;}
                if (userGroupRole.getRoleType().getId().equals(UserGroupRoleConst.MEMBER)) {return  true;}
                if (userGroupRole.getRoleType().getId().equals(UserGroupRoleConst.MODERATOR)) {return true;}
            }
            return false;
        }
        return false;
    }
}
