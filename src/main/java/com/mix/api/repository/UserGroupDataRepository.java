package com.mix.api.repository;

import com.mix.api.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Set;

@Repository
public interface UserGroupDataRepository extends JpaRepository<UserGroupData, Long> {
    public UserGroupData findUserGroupDataById(Long id);
    public Set<UserGroupData> findUserGroupDataByUserAndGroup(User user, Group group);
    public UserGroupData findUserGroupDataByTime(LocalDateTime time);
    public UserGroupData findUserGroupDataByStatus(DataStatus status);
    public Set<UserGroupData> findUserGroupDataByData(Data data);
}
