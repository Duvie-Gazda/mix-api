package com.mix.api.repository;

import com.mix.api.model.User;
import com.mix.api.model.UserData;
import com.mix.api.model.UserDataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    public UserData findUserDataById(Long id);
    public UserData findUserDataByName(String name);
    public Set<UserData> findUserDataByUserDataTypesAndUsers(Set<UserDataType> userDataTypes, Set<User> users);
}
