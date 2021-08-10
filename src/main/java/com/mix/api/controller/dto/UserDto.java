package com.mix.api.controller.dto;

import com.mix.api.model.User;
import com.mix.api.model.UserRole;
import com.mix.api.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.http.RequestEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    private User user;
    private UserRole role;

//    private UserRepository userRepository;
//    private UserRole userRole;

//    public RequestEntity<?> createUser(){}
//    public RequestEntity<?> deleteUser(){}
//    public RequestEntity<?> updateUser(){}
//    public RequestEntity<?> makeUserAdmin(){}
//    public RequestEntity<?> makeUserDeveloper(){}
//    public RequestEntity<?> deleteAllUserRoles(){}
}
