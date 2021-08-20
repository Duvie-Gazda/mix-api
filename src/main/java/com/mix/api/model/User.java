package com.mix.api.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties("userDataDataTypes")
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column( nullable = false, updatable = false, unique = true)
    private Long id;

    @Column( nullable = false, unique = true, length = 500)
    @NotBlank
    private String nick;

    @Column( nullable = false, length = 115)
    @NotBlank
    private String pass;

//    RELATIONS

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_user_role",
            inverseJoinColumns = { @JoinColumn(name = "user_id") },
            joinColumns = { @JoinColumn(name = "role_id")}
    )
    @JsonBackReference
    private Set <UserRole> userRoles;


    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<UserDataDataType> userDataDataTypes;

    @OneToMany(mappedBy = "user")
    private Set<UserGroupData> userGroupData;

    @OneToMany(mappedBy = "user")
    private Set<UserGroupRole> userGroupRoles;


}
