package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "datas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

//    RELATIONS

    @ManyToMany
    private List<DataStatus> dataStatusList;

    @ManyToMany
    private List<DataType> dataTypeList;

    @OneToMany(mappedBy = "data", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<UserGroupData> userGroupData;

}
