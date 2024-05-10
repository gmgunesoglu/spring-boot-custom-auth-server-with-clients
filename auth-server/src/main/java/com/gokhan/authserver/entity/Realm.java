package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.lang.annotation.Target;
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

//    @Column(
//            name = "super_user_id",
//            nullable = false,
//            updatable = false
//    )
//    private Long superUserId;

    @Column(
            name = "name",
            nullable = false,
            length = 30
    )
    private String name;

    @ManyToOne(targetEntity = SuperUser.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "super_user_id")
    private SuperUser superUser;

    @OneToMany(targetEntity = ResourceServer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<ResourceServer> resourceServers;

    @OneToMany(targetEntity = Policy.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<Policy> policies;

    @OneToMany(targetEntity = Scope.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<Scope> scopes;

    @OneToMany(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<Role> roles;

    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id")
    private List<User> users;
}
