package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupRoleType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, unique = true,updatable = false)
    private Long id;

    @Column
    @NotBlank
    private String name;

    public UserGroupRoleType(String name){
        this.name = name;
    }

    @JoinColumn(updatable = false, referencedColumnName = "id")
    @ManyToMany(cascade = CascadeType.REMOVE)
    private Set<UserGroupRole> role;
}
