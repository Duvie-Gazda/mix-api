package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "users" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "nick", nullable = false, unique = true, length = 500)
    @NaturalId
    private String nick;

    @Column(name = "pass", nullable = false, length = 115)
    private String pass;

//    RELATIONS

    @ManyToMany
    private Set<Group> groupList;

    @ManyToMany
    private Set <UserRole> userRoleList;

    @OneToMany(mappedBy = "user")
    private Set<UserGroupData> userGroupData;

    @OneToMany(mappedBy = "user")
    private Set<UserGroupRole> userGroupRoles;
}
