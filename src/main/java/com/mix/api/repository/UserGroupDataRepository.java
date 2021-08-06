package com.mix.api.repository;

import com.mix.api.model.UserGroupData;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserGroupDataRepository extends JpaRepository<UserGroupData, Long> {
}
