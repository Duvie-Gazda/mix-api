package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, unique = true, updatable = false)
    private Long id;

    @Column (nullable = false, unique = true, updatable = false)
    private String name;

//    RELATIONS

    @ManyToMany
    @JoinTable(
            name = "group_group_type",
            inverseJoinColumns = {@JoinColumn (name = "group_type_id")},
            joinColumns = { @JoinColumn (name = "group_id")}
    )
    private Set<Group> groups;
}
