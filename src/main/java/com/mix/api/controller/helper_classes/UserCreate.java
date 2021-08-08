package com.mix.api.controller.helper_classes;

import com.mix.api.model.User;
import com.mix.api.model.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserCreate {
    private User user;
    private UserRole role;
}
