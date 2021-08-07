package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
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
    @Column(name = "id", nullable = false, updatable = false)
    private Long id;

    @Column(name = "nick", nullable = false)
    @NaturalId
    private String nick;

    @Column(name = "pass", nullable = false)
    private String pass;

    @Column(name = "email", nullable = false)
    private String email;

//    RELATIONS

    @ManyToMany
    private Set<Group> groupList;

    @ManyToMany
    private Set <UserRole> userRoleList;

    @OneToMany(mappedBy = "user_group_datas", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserGroupData> userGroupData;

    @OneToMany(mappedBy = "user", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserGroupRole> userGroupRoles;
}
