package com.mix.api.repository;

import com.mix.api.model.Data;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Repository
public interface DataRepository extends JpaRepository<Data, Long> {
    public Data findDataById(Long id);
    public Data findDataByName(@NotBlank String name);
}
