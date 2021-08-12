package com.mix.api.repository;

import com.mix.api.model.DataType;
import com.mix.api.model.UserDataType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserDataTypeRepository extends JpaRepository<UserDataType, Long> {
    public UserDataType findUserDataTypeById(Long id);
    public Set<UserDataType> findUserDataTypesByName(String name);

    UserDataType findUserDataTypeByName(String dataTypeName);
}
