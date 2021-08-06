package com.mix.api.security;

import com.mix.api.model.User;
import com.mix.api.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Collection<? extends GrantedAuthority> grantedAuthorities;


    public static UserDetails fromUserToUserDetails(User user) {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (UserRole role :
                user.getUserRoleList()) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }

        return new UserDetails(
                user.getId(),
                user.getEmail(),
                user.getPass(),
                grantedAuthorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public Long getId() {
        return id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
