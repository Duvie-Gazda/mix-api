package com.mix.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table
@Getter
@Setter
public class
UserDataDataType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    public UserDataDataType(User user,UserData data,UserDataType dataType){
        this.user = user;
        this.data = data;
        this.dataType = dataType;
    }
    public UserDataDataType(){};


//    CONNECTIONS

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false, referencedColumnName = "id")
    @JsonBackReference
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false, referencedColumnName = "id")
    @JsonBackReference

    private UserData data;

    @ManyToOne (optional = false)
    @JoinColumn(updatable = false, referencedColumnName = "id")
    @JsonBackReference

    private UserDataType dataType;

}
