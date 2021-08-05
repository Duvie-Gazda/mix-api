package com.mix.api.repository;

import com.mix.api.model.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DataStatusRepository extends JpaRepository<DataStatus, Long> {
}
