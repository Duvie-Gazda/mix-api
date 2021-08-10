package com.mix.api.repository;

import com.mix.api.model.Group;
import com.mix.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    public User findUserByNick(String nick);
    public User findUserById(Long id);
}
