package com.mix.api.repository;

import com.mix.api.model.UserGroupRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRoleRepository extends JpaRepository<UserGroupRole, Long> {
}
