package com.mix.api.repository;

import com.mix.api.model.Group;
import com.mix.api.model.GroupData;
import com.mix.api.model.GroupDataDataType;
import com.mix.api.model.GroupDataType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface GroupDataDataTypeRepository extends JpaRepository<GroupDataDataType, Long> {
    public Set<GroupDataDataType> findGroupDataDataTypeByGroup(Group group);
    public Set<GroupDataDataType> findGroupDataDataTypeByType(GroupDataType type);
    public Set<GroupDataDataType> findGroupDataDataTypeByData(GroupData data);
    public Set<GroupDataDataType> findGroupDataDataTypeByDataAndGroupAndType(GroupData data, Group group, GroupDataType type);
    public Set<GroupDataDataType> findGroupDataDataTypeByTypeAndGroup(GroupDataType type, Group group);
    public Set<GroupDataDataType> findGroupDataDataTypeByGroupAndData(Group group, GroupData data);
}
