package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column( nullable = false)
    private String name;

//    RELATIONS

    @ManyToMany
    @JoinColumn(name = "user", nullable = false, updatable = false)
    private Set<User> users;

    @ManyToMany
    private Set<UserDataType> userDataTypes;

}
