package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToMany(mappedBy = "group")
    private Set<UserGroupData> userGroupData;

    @OneToMany(mappedBy = "group")
    private Set<UserGroupRole> userGroupRoles;

    @ManyToMany
    @JoinTable(
            name = "group_group_type",
            joinColumns = {@JoinColumn (name = "group_type_id")},
            inverseJoinColumns = { @JoinColumn (name = "group_id")}
    )
    private Set<GroupType> groupType;

}
