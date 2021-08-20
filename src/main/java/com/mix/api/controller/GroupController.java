package com.mix.api.controller;

import com.mix.api.controller.helper.PermissionHelper;
import com.mix.api.controller.service.GroupService;
import com.mix.api.controller.service.UserService;
import com.mix.api.model.*;
 import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/*
*
*                ---- GROUP ----
*
*  # create GROUP                  (/groups/new/{type_id}, put)
*  # update GROUP                  (/groups,     put)
*  # delete GROUP by id            (/groups/{id}, delete)
*  # get GROUP by id               (/groups/{id}, get)
*  # get GROUPS by USER            (/groups/by-users/{id}, get)
*  # get GROUPS by USERS           (/groups/by-users, post)
*  # get GROUPS by GROUP_DATA id   (/groups/by-data/{id}, get)
*  # get GROUPS by GROUP_DATA name (/groups/by-data/name/{name}, get)
*  # get GROUPS by GROUP_TYPE id    (/groups/by-type/{id}, get)
*
*               ----- GROUP DATA -----
*
*  # create GROUP_DATA                                            (/groups/data/new, put)
*  # create GROUP_DATA by name                                    (/groups/data/new/{name}, put)
*  # update GROUP_DATA                                            (/groups/data,     put)
*  # delete GROUP_DATA                                            (/groups/data,     delete)
*  # delete GROUP_DATA by ID                                      (/groups/date/{id}, delete)
*  # get GROUP_DATA by id                                         (/groups/data/{id}, get)
*  # get GROUP_DATA by name                                       (/groups/data/name/{name}, get)
*  # add GROUP_DATA to GROUP (id)                                 (/groups/{group_id}/data/{data_id}/types/{type_id}, put)
*  # smart add GROUP_DATA to GROUP (id) // with creating data     (/groups/{group_id}/data/name/{data_name}/types/{type_id}, put)
*  # delete GROUP_DATA from GROUP                                 (/groups/{group_id}/data, delete)
*  # get GROUP_DATA by GROUP (id)                                 (/groups/{group_id}/data, get)
*  # get GROUP_DATA by GROUP_DATA_TYPE id and GROUP               (/groups/{group_id}/data/types/{type_id}, get)
*
*               ----- GROUP DATA TYPE -----
*
*  # create GROUP_DATA_TYPE by name                                     (/groups/data/types/new/{name}, put)
*  # create GROUP_DATA_TYPE                                             (/groups/data/types/new/, put)
*  # delete GROUP_DATA_TYPE                                             (/groups/data/types, delete)
*  # update GROUP_DATA_TYPE                                             (/groups/data/types, put)
*  # delete GROUP_DATA_TYPE by id                                       (/groups/data/types/{id}, delete)
*  # get GROUP_DATA_TYPE by GROUP_DATA id (and GROUP id)                (/groups/{id}/data/{id}/types, get)
*  # get GROUP_DATA_TYPE by GROUP_DATA id                               (/groups/{group_id}/data/{id}/types, get)
*
*               ----- GROUP TYPE -----
*
*  # create GROUP_TYPE by name    (/groups/types/new/{name}, put)
*  # delete GROUP_TYPE by id      (/groups/types/{id}, delete)
*  # update GROUP_TYPE            (/groups/types, put)
*  # get GROUP_TYPE by GROUP id     (/groups/{group_id}/types, get)
*  # get all GROUP_TYPE's           (/groups/types, get)
* */

@RestController
public class GroupController {
    @Autowired
    private GroupService groupService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionHelper permissionHelper;

    @PutMapping(path = "/groups/new/{type_id}")
    public void createGroup(@PathVariable Long type_id){
        groupService.createGroupWithType(
                userService.getUserByNick(SecurityContextHolder.getContext().getAuthentication().getName()),
                groupService.getGroupTypeById(type_id)
        );
    }

    @PutMapping(path = "/groups/type/name/{type_name}/user/{user_id}")
    public void smartCreateGroup(@PathVariable String type_name, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(),userService.getUserById(user_id))){
            GroupType groupType = groupService.getGroupTypeByName(type_name);
            if(groupType == null){
                groupType = new GroupType(type_name);
            }
            groupService.createGroupType(groupType);
            groupService.createGroup(
                    userService.getUserById(user_id),
                    groupType
            );
        }
    }


    @PutMapping(path = "/groups/type/{type_id}/user/{user_id}")
    public void smartCreateGroup(@PathVariable Long type_id, @PathVariable Long user_id){
        if(permissionHelper.hasPermissions(SecurityContextHolder.getContext(),userService.getUserById(user_id))){
            GroupType groupType = groupService.getGroupTypeById(type_id);
            if(groupType == null){
                return;
            }
            groupService.createGroupType(groupType);
            groupService.createGroup(
                    userService.getUserById(user_id),
                    groupType
            );
        }
    }

    @PutMapping(path = "/groups")
    public void updateGroup(@RequestBody Group group   ){
        groupService.updateGroup(group);
    }

    @DeleteMapping(path = "/groups/{id}")
    public void deleteGroup(@PathVariable Long id){
        groupService.deleteGroup(groupService.getGroupById(id));
    }

    @GetMapping(path = "/groups/{id}")
    public Group getGroupById(@PathVariable Long id){
        return groupService.getGroupById(id);
    }

    @GetMapping(path = "/groups/by-users/{id}")
    public Set<Group> getGroupByUserId(@PathVariable Long id){
        return groupService.getGroupsByUser(userService.getUserById(id));
    }

    @PostMapping(path = "/groups/by-users")
    public Set<Group> getGroupByUserId(@RequestBody Set<User> users){
        return groupService.getGroupsByUsers(users);
    }

    @GetMapping(path = "/groups/by-data/{id}")
    public Set<Group> getGroupsByGroupDataId(@PathVariable Long id){
        return groupService.getGroupsByGroupData(groupService.getGroupDataById(id));
    }

    @GetMapping(path = "/groups/by-data/name/{name}")
    public Set<Group> getGroupByGroupDataName(@PathVariable String name){
        return groupService.getGroupsByGroupData(groupService.getGroupDataByName(name));
    }

    @GetMapping(path = "/groups/by-type/{id}")
    public Set<Group> getGroupsByGroupTypeId(@PathVariable Long id){
        return groupService.getGroupByGroupType(
                groupService.getGroupTypeById(id)
        );
    }

//   USER GROUP DATA

    @PutMapping(path = "/groups/data/new")
    public void createGroupData (@RequestBody GroupData groupData){
        groupService.createGroupData(groupData);
    }

    @PutMapping(path = "/groups/data/new/{name}")
    public void createGroupDataByName(@PathVariable String name){
        groupService.createGroupData(new GroupData(name));
    }

    @PutMapping(path = "/groups/data")
    public void updateGroupData (@RequestBody GroupData groupData){
        groupService.updateGroupData(groupData);
    }

    @DeleteMapping(path = "/groups/data/{id}}")
    public void deleteGroupDataByName(@PathVariable Long id){
        groupService.deleteGroupData(groupService.getGroupDataById(id));
    }

    @DeleteMapping(path = "/groups/data")
    public void deleteGroupData (@RequestBody GroupData groupData){
        groupService.deleteGroupData(groupData);
    }

    @GetMapping(path = "/groups/data/{id}")
    public GroupData getGroupDataById(@PathVariable Long id){
        return groupService.getGroupDataById(id);
    }

    @GetMapping(path = "/groups/data/name/{name}")
    public GroupData getGroupDataByName(@PathVariable String name){
        return groupService.getGroupDataByName(name);
    }

    @PutMapping(path = "/groups/{group_id}/data/{data_id}/types/{type_id}")
    public void connectGroupDataToGroup(@PathVariable Long data_id, @PathVariable Long group_id, @PathVariable Long type_id){
        groupService.addGroupDataToGroup(
                groupService.getGroupDataById(data_id),
                groupService.getGroupById(group_id),
                groupService.getGroupDataTypeById(type_id)
        );
    }

    @PutMapping(path = "/groups/{group_id}/data/name/{data_name}/types/{type_id}")
    public void smartConnectGroupDataToGroup(@PathVariable String data_name, @PathVariable Long group_id, @PathVariable Long type_id){
        GroupData groupData = new GroupData(data_name);
        groupService.createGroupData(groupData);
        groupService.addGroupDataToGroup(
            groupData,
            groupService.getGroupById(group_id),
            groupService.getGroupDataTypeById(type_id)
        );
    }

    @DeleteMapping(path = "/groups/{group_id}/data/{data_id}}")
    public void deleteGroupDataFromGroup(@PathVariable Long group_id, @PathVariable Long data_id){
            groupService.deleteGroupDataFromGroup(
                    groupService.getGroupDataById(data_id),
                    groupService.getGroupById(group_id)
            );
    }

    @GetMapping(path = "/groups/{group_id}/data")
    public Set<GroupData> getGroupDataByGroupId(@PathVariable Long group_id){
        return groupService.getGroupDataByGroup(groupService.getGroupById(group_id));
    }

    @GetMapping(path = "/groups/{group_id}/data/types/{type_id}")
    public Set<GroupData> getGroupDataByDataTypeId(@PathVariable Long group_id, @PathVariable Long type_id){
        return groupService.getGroupDataByGroupDataTypeAndGroup(groupService.getGroupDataTypeById(type_id), groupService.getGroupById(group_id));
    }

//    GROUP DATA TYPE

    @PutMapping(path = "/groups/data/types/new/{name}")
    public void createGroupDataTypeByName(@PathVariable String name) {
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())){
            groupService.createGroupDataType(new GroupDataType(name));
        }
    }

    @PutMapping(path = "/groups/data/types/")
    public void updateGroupDataType(@RequestBody GroupDataType groupDataType ) {
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            groupService.updateGroupDataType(groupDataType);
        }
    }

    @DeleteMapping(path = "/groups/data/types")
    public void deleteGroupDataType(@RequestBody GroupDataType groupDataType ) {
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            groupService.deleteGroupDataType(groupDataType);
        }
    }

    @GetMapping(path = "/groups/data/{id}/types")
    public Set<GroupDataType> getGroupDataTypeByGroupData(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {

            return groupService.getGroupDataTypeByGroupData(
                    groupService.getGroupDataById(id)
            );
        }
        return null;
    }

    @GetMapping(path = "/groups/{group_id}/data/{id}/types")
    public Set<GroupDataType> getGroupDataTypeByGroupDataAndGroup(@PathVariable Long id, @PathVariable Long group_id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return groupService.getGroupDataTypeByGroupDataAndGroup(
                    groupService.getGroupDataById(id),
                    groupService.getGroupById(group_id)
            );
        }
        return null;
    }

//    GROUP TYPE

    @PutMapping(path = "/groups/types/new/{name}")
    public void createGroupType(@PathVariable String name){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            groupService.createGroupType(new GroupType(name));
        }
    }

    @DeleteMapping(path = "/groups/types/{id}")
    public void deleteGroupType(@PathVariable Long id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            groupService.deleteGroupType(groupService.getGroupTypeById(id));
        }
    }

    @PutMapping(path = "/groups/types")
    public void updateGroupType(@RequestBody GroupType groupType){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            groupService.updateGroupType(groupType);
        }
    }

    @GetMapping(path = "/groups/{group_id}/types")
    public Set<GroupType> getGroupTypeByGroupId(@PathVariable Long group_id){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return groupService.getGroupTypeByGroup(groupService.getGroupById(group_id));
        }
        return null;
    }

    @GetMapping(path = "/groups/types")
    public Set<GroupType> getAllGroupType(){
        if(permissionHelper.isAdmin(SecurityContextHolder.getContext())) {
            return groupService.getAllGroupTypes();
        }
        return null;
    }

}
