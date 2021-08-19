package com.mix.api.repository;

import com.mix.api.model.User;
import com.mix.api.model.UserData;
import com.mix.api.model.UserDataType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
    UserData findUserDataById(Long id);
    UserData findUserDataByName(String name);
}
