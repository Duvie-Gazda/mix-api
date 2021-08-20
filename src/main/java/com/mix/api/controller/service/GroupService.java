package com.mix.api.controller.service;

import com.mix.api.model.*;
import com.mix.api.repository.*;
import org.hibernate.property.access.spi.SetterMethodImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;



/*
*    ---- GROUP ----
*
* # create GROUP
* # update GROUP
* # delete GROUP
* # get GROUP by id
* # add GROUP_TYPE to GROUP
* # get GROUPS by USER
* # get GROUPS by USERS
* # get GROUPS by GROUP_DATA
* # get GROUPS by GROUP_TYPE
* # get GROUP by GROUP_DATA_TYPE and GROUP
*
*    ---- GROUP DATA ----
*
* # create GROUP_DATA
* # update GROUP_DATA
* # delete GROUP_DATA
* # get GROUP_DATA by id
* # get GROUP_DATA by name
* # get GROUP_DATA by GROUP
* # get GROUP_DATA by GROUP_DATA_TYPE
* # add GROUP_DATA to GROUP
* # delete GROUP_DATA from GROUP
* # get GROUP_DATA BY GROUP_DATA_TYPE and GROUP
*
*   ----- GROUP DATA TYPE -----
*
* # create GROUP_DATA_TYPE
* # update GROUP_DATA_TYPE
* # delete GROUP_DATA_TYPE
* # get GROUP_DATA_TYPE by id
* # get GROUP_DATA_TYPE by name
* # get GROUP_DATA_TYPE by GROUP_DATA
* # get GROUP_DATA_TYPE by GROUP
*
*   ----- GROUP TYPE ----
*
* # create GROUP_TYPE
* # delete GROUP_TYPE
* # update GROUP_TYPE
* # get GROUP_TYPE by id
* # get GROUP_TYPE by name
* # get GROUP_TYPE by GROUP
* # get all GROUP_TYPES
*
* */

@Component
public class GroupService {
    private final String deleteUserGroupDataStatus = "deleted";

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private GroupDataRepository groupDataRepository;

    @Autowired
    private GroupDataTypeRepository groupDataTypeRepository;

    @Autowired
    private GroupDataDataTypeRepository groupDataDataTypeRepository;

    @Autowired
    private GroupTypeRepository groupTypeRepository;

    @Autowired
    private UserGroupRoleRepository userGroupRoleRepository;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private UserGroupRoleTypeRepository userGroupRoleTypeRepository;



//  GROUP

    public HttpStatus createGroup (User owner, GroupType groupType){
        try{
            Group group = new Group();
            groupRepository.save(group);
            userGroupService.addUserToGroup(group, owner, userGroupRoleTypeRepository.findUserGroupRoleTypeByName("admin"));
            this.addTypeToGroup(group, groupType);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus createGroupWithType (User owner, GroupType groupType){
        try{
            Group group = new Group();
            HashSet<GroupType> groupTypes = new HashSet<>();
            groupTypes.add(groupType);
            group.setGroupTypes(groupTypes);
            groupRepository.save(group);
//            this.addUserToGroup(group, owner, userGroupRoleTypeRepository.findUserGroupRoleTypeByName("admin"));
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public Set<Group> getGroupsByGroupDataAndGroup(GroupData groupData, Group group){
        Set<Group> groups = new HashSet<>();
        Set<GroupDataDataType> groupDataDataTypes = groupDataDataTypeRepository.findGroupDataDataTypeByGroupAndData(group, groupData);
        for(GroupDataDataType groupDataDataType: groupDataDataTypes){
            groups.add(groupDataDataType.getGroup());
        }
        return groups;
    }

    public void updateGroup (Group group){
        groupRepository.save(group);
    }

    public void deleteGroup (Group group){
        groupRepository.delete(group);
    }

    public void addTypeToGroup (Group group, GroupType type){
        if(group.getGroupTypes() == null){
            group.setGroupTypes(new HashSet<>());
        }
        group.getGroupTypes().add(type);
        groupRepository.save(group);
    }

    public Group getGroupById (Long id){
        return groupRepository.findGroupById(id);
    }
    
    public Set<Group> getGroupsByUser(User user){
        Set<Group> groups = new HashSet<>();
        Set<UserGroupRole> all = userGroupRoleRepository.findUserGroupRolesByUser(user);
        for(UserGroupRole userGroupRole : all){
            groups.add(userGroupRole.getGroup());
        }
        return groups;
    }

    public Set<Group> getGroupsByUsers(Set<User> users){
        Set<Group> groups = new HashSet<>();
        for(User user : users) {
            Set<UserGroupRole> all = userGroupRoleRepository.findUserGroupRolesByUser(user);
            for (UserGroupRole userGroupRole : all) {
                groups.add(userGroupRole.getGroup());
            }
        }
        return groups;
    }

    public Set<Group> getGroupByGroupType(GroupType groupType){
        return groupType.getGroups();
    }


    public Set<Group> getGroupsByGroupData(GroupData groupData){
        Set<Group> groups = new HashSet<>();
        Set<GroupDataDataType> all = groupDataDataTypeRepository.findGroupDataDataTypeByData(groupData);
        for(GroupDataDataType dataType : all){
            groups.add(dataType.getGroup());
        }
        return groups;
    }

//   GROUP TYPE

    public HttpStatus createGroupType(GroupType groupType){
        try{
            groupTypeRepository.save(groupType);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus updateGroupType(GroupType groupType){
        try{
            groupTypeRepository.save(groupType);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteGroupType(GroupType groupType){
        try{
            groupTypeRepository.delete(groupType);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public GroupType getGroupTypeById(Long id){
        return groupTypeRepository.findGroupTypeById(id);
    }

    public GroupType getGroupTypeByName(String name){
        return groupTypeRepository.findGroupTypeByName(name);
    }

    public Set<GroupType> getGroupTypeByGroup(Group group){
        return group.getGroupTypes();
    }

    public Set<GroupType> getAllGroupTypes(){
        return groupTypeRepository.findAll();
    }

//  GROUP DATA

    public HttpStatus createGroupData(GroupData groupData){
        try{
            groupDataRepository.save(groupData);
        }catch (Throwable throwable){
            return  HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteGroupData(GroupData groupData){
        try{
            groupDataRepository.delete(groupData);
        }catch (Throwable throwable){
            return  HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus updateGroupData(GroupData groupData){
        try{
            groupDataRepository.save(groupData);
        }catch (Throwable throwable){
            return  HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public GroupData getGroupDataById(Long id){
        return groupDataRepository.findGroupDataById(id);
    }

    public GroupData getGroupDataByName(String name){
        return groupDataRepository.findGroupDataByName(name);
    }

    public Set<GroupData> getGroupDataByGroup (Group group){
        Set<GroupData> groupData = new HashSet<>();
        Set<GroupDataDataType> all = groupDataDataTypeRepository.findGroupDataDataTypeByGroup(group);
        for(GroupDataDataType dataType : all){
            groupData.add(dataType.getData());
        }
        return groupData;
    }

    public Set<GroupData> getGroupDataByGroupDataType(GroupDataType groupDataType){
        Set<GroupData> groupData = new HashSet<>();
        Set<GroupDataDataType> all = groupDataDataTypeRepository.findGroupDataDataTypeByType(groupDataType);
        for(GroupDataDataType dataType : all){
            groupData.add(dataType.getData());
        }
        return groupData;
    }

    public Set<GroupData> getGroupDataByGroupDataTypeAndGroup(GroupDataType groupDataType, Group group){
        Set<GroupData> groupData = new HashSet<>();
        Set<GroupDataDataType> all = groupDataDataTypeRepository.findGroupDataDataTypeByTypeAndGroup(groupDataType,group);
        for(GroupDataDataType dataType : all){
            groupData.add(dataType.getData());
        }
        return groupData;
    }

    public HttpStatus addGroupDataToGroup(GroupData data, Group group, GroupDataType groupDataType){
        try{
            groupDataDataTypeRepository.save(new GroupDataDataType(group,data,groupDataType));
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteGroupDataFromGroup(GroupData data, Group group){
        try{
            Set<GroupDataDataType> groupDataDataTypes = groupDataDataTypeRepository.findGroupDataDataTypeByGroupAndData(group, data);
            groupDataDataTypeRepository.deleteAll(groupDataDataTypes);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }


//  GROUP DATA TYPE

    public HttpStatus createGroupDataType (GroupDataType groupDataType){
        try{
            groupDataTypeRepository.save(groupDataType);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus updateGroupDataType (GroupDataType groupDataType){
        try{
            groupDataTypeRepository.save(groupDataType);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public HttpStatus deleteGroupDataType (GroupDataType groupDataType){
        try{
            groupDataTypeRepository.delete(groupDataType);
        }catch (Throwable throwable){
            return HttpStatus.BAD_GATEWAY;
        }
        return HttpStatus.OK;
    }

    public GroupDataType getGroupDataTypeById (Long id){
        return groupDataTypeRepository.findGroupDataTypeById(id);
    }

    public GroupDataType getGroupDataTypeByName (String name){
        return groupDataTypeRepository.findGroupDataTypeByName(name);
    }

    public Set<GroupDataType> getGroupDataTypeByGroupData(GroupData data){
        Set<GroupDataType> groupDataTypes = new HashSet<>();
        Set<GroupDataDataType> all = groupDataDataTypeRepository.findGroupDataDataTypeByData(data);
        for(GroupDataDataType dataType : all){
            groupDataTypes.add(dataType.getType());
        }
        return groupDataTypes;
    }

    public Set<GroupDataType> getGroupDataTypeByGroupDataAndGroup(GroupData data, Group group){
        Set<GroupDataType> groupDataTypes = new HashSet<>();
        Set<GroupDataDataType> all = groupDataDataTypeRepository.findGroupDataDataTypeByGroupAndData(group,data);
        for(GroupDataDataType dataType : all){
            groupDataTypes.add(dataType.getType());
        }
        return groupDataTypes;
    }

    public Set<GroupDataType> getGroupDataTypeByGroup (Group group){
        Set<GroupDataType> groupDataType = new HashSet<>();
        Set<GroupDataDataType> all = groupDataDataTypeRepository.findGroupDataDataTypeByGroup(group);
        for(GroupDataDataType dataType : all){
            groupDataType.add(dataType.getType());
        }
        return groupDataType;
    }

}



