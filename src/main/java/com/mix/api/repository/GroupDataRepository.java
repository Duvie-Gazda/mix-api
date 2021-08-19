package com.mix.api.repository;

import com.mix.api.model.GroupData;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.validation.constraints.NotBlank;

public interface GroupDataRepository extends JpaRepository<GroupData, Long> {
    public GroupData findGroupDataById(Long id);
    public GroupData findGroupDataByName(@NotBlank String name);
}
