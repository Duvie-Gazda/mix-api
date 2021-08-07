package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "data_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

//    RELATIONS

    @ManyToMany (cascade =  CascadeType.REMOVE)
    private Set<Data> dataList;
}
