package com.mix.api.repository;

import com.mix.api.model.GroupType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface GroupTypeRepository extends CrudRepository<GroupType, Long> {
    public GroupType findGroupTypeByName(String name);
    public GroupType findGroupTypeById(Long id);
    public Set<GroupType> findAll();
}
