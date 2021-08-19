package com.mix.api.repository;

import com.mix.api.model.UserGroupRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRoleTypeRepository extends JpaRepository<UserGroupRoleType, Long> {
    public UserGroupRoleType findUserGroupRoleTypeByName(String name);
    public UserGroupRoleType findUserGroupRoleTypeById(Long id);
}
