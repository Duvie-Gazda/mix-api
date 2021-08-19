package com.mix.api.controller;

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

    @MessageMapping("/add/groups/{group_id}/users/{user_id}/data/name/{data_name}/type/{type_id}")
    @SendTo("topic/groups/{group_id}/users/data")
    public UserGroupData addMessage(@DestinationVariable Long group_id, @DestinationVariable Long user_id, @DestinationVariable String data_name, @DestinationVariable Long type_id){
        if(hasPermissionsToAddMessage(SecurityContextHolder.getContext(), groupService.getGroupById(type_id))){
            Data data = new Data(data_name);
            dataService.createData(data);

        }
        return null;
    }

    private boolean hasPermissionsToAddMessage(SecurityContext securityContext, Group group){
        User currentUser = userService.getUserByNick(
                ((UserDetails) securityContext.getAuthentication().getCredentials()).getUsername()
        );
        Set<User> users = groupUserService.getUserByGroup(group);
        if(users.contains(currentUser)){
            Set<UserGroupRole> userGroupRoles = groupUserService.getUserGroupRoleByUser(userService.getUserByNick(currentUser.getNick()));
            for (UserGroupRole userGroupRole: userGroupRoles){
                if (userGroupRole.getRoleType().equals("admin")){
                    return  true;
                }
            }
            return false;
        }
        return false;
    }
}
