package com.mix.api.repository;

import com.mix.api.model.Group;
import com.mix.api.model.User;
import com.mix.api.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findUserByNick(String nick);
    public User findUserById(Long id);
    public Set<User> findAll();
    public Set<User> findByUserRoles_Name(String userRoles_name);

    Set<User> findByUserRoles_Id(Long id);

}
