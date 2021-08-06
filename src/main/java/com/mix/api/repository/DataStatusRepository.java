package com.mix.api.repository;

import com.mix.api.model.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DataStatusRepository extends JpaRepository<DataStatus, Long> {
}
