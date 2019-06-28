package org.dadeco.cu996.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "Users")
@Setter
@Getter
@NoArgsConstructor
@RequiredArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NonNull
    @Column(name = "name")
    @JsonProperty
    private String name;

    @NonNull
    @Column(name = "email", unique = true)
    @JsonProperty
    private String email;

    @NonNull
    @Column(name = "password")
    @JsonProperty
    private String password;

    @Column(name = "nt_account", unique = true)
    @NonNull
    @JsonProperty
    private String ntAccount;

    /*@ManyToMany
    @JoinTable(
            name = "users_roles",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"))
    @JsonProperty
    private Set<Role> roles;*/

    @JsonProperty
    @NonNull
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

}
