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
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    public UserData(String name) {
        this.name = name;
    }

    public UserData() {}
    //    RELATIONS


    @OneToMany( mappedBy = "dataType")
    private Set<UserDataDataType> dataTypes;
}
