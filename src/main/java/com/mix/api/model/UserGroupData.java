package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, updatable = false, unique = true)
    private Long id;

    @Column( updatable = false, nullable = false)
    private LocalDateTime time;

//    CONNECTIONS

    @ManyToOne( optional = false)
    @JoinColumn(updatable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false)
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn
    private Data data;


    @ManyToOne(optional = false)
    @JoinColumn
    private DataType dataType;

    @JoinColumn
    @ManyToOne
    private DataStatus status;


}
