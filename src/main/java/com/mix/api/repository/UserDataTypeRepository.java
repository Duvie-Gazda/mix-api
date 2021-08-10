package com.mix.api.repository;

import com.mix.api.model.UserDataType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDataTypeRepository extends JpaRepository<UserDataType, Long> {
}
