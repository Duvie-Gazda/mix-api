package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "users" )
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "nick", nullable = false)
    private String nick;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "email", nullable = false)
    private String email;

//    RELATIONS

    @ManyToMany
    private List<Group> groupList;

    @ManyToMany
    private List<UserRole> userRoleList;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserGroupData> userGroupData;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserGroupRole> userGroupRoles;
}
