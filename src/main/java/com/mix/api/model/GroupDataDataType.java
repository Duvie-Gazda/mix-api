package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GroupDataDataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public GroupDataDataType (Group group, GroupData groupData, GroupDataType dataType){
        this.data = groupData;
        this.group = group;
        this.type = dataType;
    }


//    RELATIONS

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false)
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false)
    private GroupData data;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false)
    private GroupDataType type;
}
