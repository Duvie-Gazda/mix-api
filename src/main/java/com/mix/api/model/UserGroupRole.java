package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "user_group_roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRole{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

//    CONNECTIONS

    @JoinColumn(name = "user")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    @JoinColumn(name = "group")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Group group;

    @JoinColumn(name = "user_group_role_type")
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private UserGroupRoleType roleType;
}
