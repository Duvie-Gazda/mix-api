package com.mix.api.controller.dto;


import com.mix.api.model.User;
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
    private String nick;

    public FastUserDto(User user) {
        this.nick = user.getNick();
    }
}
