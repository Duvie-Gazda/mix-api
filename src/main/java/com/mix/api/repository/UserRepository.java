package com.mix.api.repository;

import com.mix.api.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    public User findUserByNick(String nick);
    public User findUserById(Long id);
}
