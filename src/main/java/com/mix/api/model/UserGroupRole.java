package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRole{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, unique = true, updatable = false)
    private Long id;

    public UserGroupRole(User user, Group group, UserGroupRoleType roleType){
        this.group = group;
        this.user = user;
        this.roleType = roleType;
    }


//    CONNECTIONS

    @JoinColumn(updatable = false)
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    private User user;

    @JoinColumn(updatable = false)
    @ManyToOne(cascade = CascadeType.REMOVE,optional = false)
    private Group group;

    @JoinColumn(updatable = false)
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    private UserGroupRoleType roleType;
}
