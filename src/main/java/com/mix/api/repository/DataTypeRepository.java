package com.mix.api.repository;

import com.mix.api.model.DataType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataTypeRepository extends JpaRepository<DataType, Long> {
}
