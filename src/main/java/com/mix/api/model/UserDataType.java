package com.mix.api.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`user_data_type`")
@Getter
@Setter
@NoArgsConstructor
public class UserDataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, unique = true, updatable = false)
    private Long id;

    @Column( nullable = false, unique = true)
    private String name;

//    RELATIONS

    @ManyToMany
    private Set<UserData> userDataSet;

}
