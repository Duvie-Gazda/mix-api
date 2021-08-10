package com.mix.api.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.lang.Nullable;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FastUserDto {
    @Nullable
    private Long id;
    @Nullable
    private String nick;

    @Nullable
    private List<Long> roleIds;
    @Nullable
    private List<Long> groupDataIds;
    @Nullable
    private List<Long> groupRoleIds;

    @Nullable
    private List<String> roleNames;
    @Nullable
    private List<String> groupNames;
    @Nullable
    private List<String> groupDataNames;
    @Nullable
    private List<String> groupRoleNames;
}
