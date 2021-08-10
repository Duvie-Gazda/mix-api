package com.mix.api.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.mix.api.model.GroupDataType;

public interface GroupDataTypeRepository extends JpaRepository<GroupDataType, Long> {
    public GroupDataType findGroupDataTypeById(Long id);
    public GroupDataType findGroupDataTypeByName(String name);
}
