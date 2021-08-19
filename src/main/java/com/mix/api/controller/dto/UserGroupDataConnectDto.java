package com.mix.api.controller.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserGroupDataConnectDto {
    private Long group_id;
    private Long data_id;
    private Long user_id;
    private Long dataType_id;
}
