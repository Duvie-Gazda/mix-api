package com.mix.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "`user_data_type`")
@Getter
@Setter
@AllArgsConstructor
@JsonIgnoreProperties("dataTypes")
public class UserDataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, unique = true, updatable = false)
    private Long id;

    @Column( nullable = false, unique = true)
    private String name;

    public UserDataType(String name) {
        this.name = name;
    }

    public UserDataType() {}
    //    RELATIONS

    @OneToMany(mappedBy = "dataType")
    @JsonManagedReference
    private Set<UserDataDataType> dataTypes;

}
