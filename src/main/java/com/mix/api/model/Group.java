package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "`group`")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true,updatable = false)
    private Long id;

//    RELATIONS

    @ManyToMany(targetEntity =  GroupType.class)
    private Set<GroupType> groupTypeList;

    @ManyToMany(targetEntity = User.class)
    private Set<User> users;

    @OneToMany(mappedBy = "group")
    private Set<UserGroupData> userGroupData;

    @OneToMany(mappedBy = "group")
    private Set<UserGroupRole> userGroupRoles;
}
