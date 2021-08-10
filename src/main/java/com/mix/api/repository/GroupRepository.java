package com.mix.api.repository;

import com.mix.api.model.Group;
import com.mix.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    public Group findGroupById(Long id);
}
