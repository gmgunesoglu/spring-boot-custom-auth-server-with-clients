package com.gokhan.authserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "role",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "realm_id"})}
)
public class Role implements GrantedAuthority {

    @Id
    @SequenceGenerator(
            name = "role_seq",
            sequenceName = "role_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "role_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "name",
            nullable = false,
            length = 30
    )
    private String name;

//    @Column(
//            name = "realm_id",
//            nullable = false
//    )
//    private Long realmId;

    @Override
    public String getAuthority() {
        return this.getName();
    }

    @ManyToOne(targetEntity = Realm.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id")
    private Realm realm;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    @JsonIgnore
    private List<User> users;

    @ManyToMany(mappedBy = "roles", fetch = FetchType.LAZY)
    private List<Policy> policies;
}