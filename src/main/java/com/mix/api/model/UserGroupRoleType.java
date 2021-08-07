package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_group_role_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRoleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true,updatable = false)
    private Long id;

    @JoinColumn(name =  "user", updatable = false)
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)

    private User user;

    @JoinColumn(name = "group", updatable = false)
    @ManyToOne(cascade = CascadeType.REMOVE, optional = false)
    private Group group;

    @JoinColumn(name = "user_group_role",updatable = false)
    @ManyToOne(cascade = CascadeType.REMOVE,optional = false)
    private UserGroupRole role;
}
