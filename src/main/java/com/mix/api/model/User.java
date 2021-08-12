package com.mix.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, updatable = false, unique = true)
    private Long id;

    @Column( nullable = false, unique = true, length = 500)
    private String nick;

    @Column( nullable = false, length = 115)
    private String pass;

//    RELATIONS

    @ManyToMany
    @JoinTable(
            name = "user_user_role",
            inverseJoinColumns = { @JoinColumn(name = "user_id") },
            joinColumns = { @JoinColumn(name = "role_id")}
    )
    @JsonBackReference
    private Set <UserRole> userRoles;


    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<UserDataDataType> userDataDataTypes;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<UserGroupData> userGroupData;

    @OneToMany(mappedBy = "user")
    @JsonBackReference
    private Set<UserGroupRole> userGroupRoles;


}
