package com.mix.api.repository;

import com.mix.api.model.User;
import com.mix.api.model.UserData;
import com.mix.api.model.UserDataDataType;
import com.mix.api.model.UserDataType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface UserDataDataTypeRepository extends JpaRepository<UserDataDataType, Long> {
    public UserDataDataType findUserDataDataTypeByData(UserData data);
    public UserDataDataType findUserDataDataTypeByDataType(UserDataType dataType);
    public Set<UserDataDataType> findUserDataDataTypesByData(UserData data);
    public Set<UserDataDataType> findUserDataDataTypesByDataType(UserDataType dataType);
    public Set<UserDataDataType> findUserDataDataTypeByUser(User user);

    public UserDataDataType findUserDataDataTypeByUserAndDataAndDataType(User user, UserData data, UserDataType dataType);
    public UserDataDataType findUserDataDataTypeByUserAndData(User user, UserData data);
}
