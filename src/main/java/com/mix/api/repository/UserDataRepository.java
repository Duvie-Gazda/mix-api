package com.mix.api.repository;

import com.mix.api.model.User;
import com.mix.api.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Long> {
}
