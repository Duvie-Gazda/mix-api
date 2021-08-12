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
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true,updatable = false)
    private Long id;

    @Column( nullable = false)
    private String name;

//    RELATIONS

    @OneToMany(mappedBy = "data")
    private Set<UserGroupData> userGroupData;

}
