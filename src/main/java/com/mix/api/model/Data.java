package com.mix.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
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
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, unique = true,updatable = false)
    private Long id;

    @Column( nullable = false, length = 500)
    @NotBlank
    private String name;

    public Data (String name){
        this.name = name;
    }

//    RELATIONS

    @OneToMany(mappedBy = "data")
    @JsonBackReference
    private Set<UserGroupData> userGroupData;

}
