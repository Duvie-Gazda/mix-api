package com.mix.api.controller;

import com.mix.api.controller.consts.UserGroupRoleConst;
import com.mix.api.controller.service.DataService;
import com.mix.api.controller.service.GroupService;
import com.mix.api.controller.service.UserGroupService;
import com.mix.api.controller.service.UserService;
import com.mix.api.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/*
*                ----- GROUP USER -----
 *
 *  # add USER to the GROUP        (/groups/{group_id}/users/{user_id}, put)
 *  # delete USER from GROUP       (/groups/{group_id}/users/{user_id}, delete)
 *  # get USERS from GROUP by id   (/groups/{group_id}/users, get)
 *
 *               ----- GROUP USER ROLE ----
 *
 *  # create GROUP_USER_ROLE                        (/groups/users/roles/new/{name}, put)
 *  # delete GROUP_USER_ROLE by id                  (/groups/users/roles/{id}, delete)
 *  # get USER_GROUP_ROLE by GROUP id and User id   (/groups/{group_id}/users/{user_id}/roles, get)
 *
* */

@RestController
public class GroupUserController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserService userService;

    @Autowired
    private DataService dataService;


//   GROUP USER DATA



//   GROUP USER

    @PutMapping(path = "/groups/{group_id}/users/{user_id}")
    public void addUserToGroup(@PathVariable Long group_id, @PathVariable Long user_id) {
        userGroupService.addUserToGroup(
                groupService.getGroupById(group_id),
                userService.getUserById(user_id),
                userGroupService.getUserGroupRoleTypeById(UserGroupRoleConst.ADMIN)
        );
    }

    @DeleteMapping(path = "/groups/{group_id}/users/{user_id}")
    public void deleteUserFromGroup(@PathVariable Long group_id, @PathVariable Long user_id) {
        userGroupService.deleteUserGroupRoleFromUser(
                groupService.getGroupById(group_id),
                userService.getUserById(user_id)
        );
    }

    @GetMapping(path = "/groups/{group_id}/users")
    public Set<User> getUsersByGroupId(@PathVariable Long group_id) {
        return userGroupService.getUserByGroup(
                groupService.getGroupById(group_id)
        );
    }


//    GROUP USER ROLE

    @PutMapping(path = "/groups/users/roles/new/{name}")
    public void createGroupUserRole(@PathVariable String name) {
        userGroupService.createUserGroupRoleType(new UserGroupRoleType(name));
    }

    @DeleteMapping(path = "/groups/users/roles/{id}")
    public void deleteUserGroupRoleType(@PathVariable Long id) {
        userGroupService.deleteUserGroupRoleType(
                userGroupService.getUserGroupRoleTypeById(id)
        );
    }

    @GetMapping(path = "/groups/users/roles/{id}")
    public UserGroupRoleType getUserGroupRoleTypeById(@PathVariable Long id) {
        return userGroupService.getUserGroupRoleTypeById(id);
    }

    @GetMapping(path = "/groups/{group_id}/users/{user_id}/roles")
    public Set<UserGroupRoleType> getUserGroupRoleTypeByGroupId(@PathVariable Long group_id, @PathVariable Long user_id) {
        return userGroupService.getUserGroupRoleTypesByUserAndGroup(
                userService.getUserById(user_id),
                groupService.getGroupById(group_id)
        );
    }

}
