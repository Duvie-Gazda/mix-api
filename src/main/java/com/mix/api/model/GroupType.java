package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "group_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

//    RELATIONS

    @ManyToMany(targetEntity =  Group.class)
    private Set<Group> groups;
}
