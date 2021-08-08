package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "data_statuses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DataStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true, updatable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, updatable = false)
    private String name;

//    RELATIONS

    @ManyToMany
    private Set<Data> dataList;

}
