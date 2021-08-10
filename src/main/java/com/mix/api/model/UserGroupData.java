package com.mix.api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_group_datas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserGroupData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "time", updatable = false, nullable = false)
    private LocalDateTime time;

//    CONNECTIONS

    @ManyToOne( optional = false)
    @JoinColumn(updatable = false)
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false)
    private Group group;

    @ManyToOne(optional = false)
    @JoinColumn(updatable = false)
    private Data data;

    @JoinColumn ( updatable = false)
    @ManyToOne
    private DataStatus status;


}
