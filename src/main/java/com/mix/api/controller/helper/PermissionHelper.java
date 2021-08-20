package com.mix.api.controller.helper;

import com.mix.api.controller.consts.UserGroupRoleConst;
import com.mix.api.controller.service.UserService;
import com.mix.api.model.User;
import com.mix.api.model.UserRole;
import com.mix.api.security.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PermissionHelper {
    @Autowired
    private UserService userService;
    //    Helpers

    public boolean hasPermissions(SecurityContext securityContext, User userEvent){
        UserDetails currentUser = (UserDetails) securityContext.getAuthentication().getCredentials();
        User user = userService.getUserByNick(currentUser.getUsername());
        return isAdmin(securityContext) || userEvent.getId().equals(user.getId());
    }

    public boolean isAdmin(SecurityContext securityContext){
        UserDetails currentUser = (UserDetails) securityContext.getAuthentication().getPrincipal();
        User user = userService.getUserByNick(currentUser.getUsername());
        Set<UserRole> userRoles = new HashSet<>();
        userRoles.add(
                userService.getUserRoleById(UserGroupRoleConst.ADMIN)
        );
        return user.getUserRoles().containsAll(userRoles);
    }
}
