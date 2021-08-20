package com.mix.api.security;

import com.mix.api.model.User;
import com.mix.api.model.UserRole;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

@AllArgsConstructor
public class UserDetails implements org.springframework.security.core.userdetails.UserDetails {

    private final Long id;
    private final String nick;
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
                user.getNick(),
                user.getPass(),
                grantedAuthorities);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return grantedAuthorities;
    }

    public UserDetails(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public static UserDetails fromUserToUserDetails(User user) {
        Collection<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        Set<UserRole> userRoles = user.getUserRoles();
        for (UserRole role : userRoles) {
            grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_" + role.getName()));
        }



        return new UserDetails(
                user.getId(),
                user.getNick(),
                user.getPass(),
                grantedAuthorities);
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
        return nick;
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
