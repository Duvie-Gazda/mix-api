package com.mix.api.repository;

import com.mix.api.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    public UserRole findUserRoleById(Long id);
    public UserRole findUserRoleByName(String name);
}
