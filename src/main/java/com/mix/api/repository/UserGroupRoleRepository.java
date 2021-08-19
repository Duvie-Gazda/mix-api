package com.mix.api.repository;

import com.mix.api.model.Group;
import com.mix.api.model.User;
import com.mix.api.model.UserGroupRole;
import com.mix.api.model.UserGroupRoleType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserGroupRoleRepository extends JpaRepository<UserGroupRole, Long> {
//    @Query("delete from UserGroupRole where UserGroupRole.user.id = ?1 and UserGroupRole.group.id = ?2")
//    public void deleteUserFromGroup(Long userId, Long groupId);

    public Set<UserGroupRole> findUserGroupRolesByUser(User user);
    public UserGroupRole findUserGroupRoleByGroupAndUserAndRoleType(Group group, User user, UserGroupRoleType roleType);
    public Set<UserGroupRole> findUserGroupRoleByGroupAndUser(Group group, User user);
    public Set<UserGroupRole> findUserGroupRoleByGroup(Group group);
    public UserGroupRole findUserGroupRoleById(Long id);
    public Set<UserGroupRole> findUserGroupRoleByRoleType(UserGroupRoleType roleType);
}
