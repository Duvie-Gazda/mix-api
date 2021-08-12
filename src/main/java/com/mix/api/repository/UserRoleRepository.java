package com.mix.api.repository;

import com.mix.api.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    public UserRole findUserRoleById(Long id);
    public UserRole findUserRoleByName(String name);
    public List<UserRole> findAll();
}
