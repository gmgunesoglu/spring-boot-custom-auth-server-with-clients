package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "realm",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "super_user_id"})}
)
public class Realm {

    @Id
    @SequenceGenerator(
            name = "realm_seq",
            sequenceName = "realm_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "realm_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "super_user_id",
            nullable = false,
            updatable = false
    )
    private Long superUserId;

    @Column(
            name = "name",
            nullable = false
    )
    private String name;

    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<User> users;

    @OneToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<Role> roles;

    @OneToMany(targetEntity = Client.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<Client> clients;
}
