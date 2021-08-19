package com.mix.api.repository;

import com.mix.api.model.DataStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;

@Repository
public interface DataStatusRepository extends JpaRepository<DataStatus, Long> {
    public DataStatus findDataStatusByName(@NotBlank String name);
    public DataStatus findDataStatusById(Long id);
}
