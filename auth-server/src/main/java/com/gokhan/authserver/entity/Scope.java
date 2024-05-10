package com.gokhan.authserver.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
        name = "scope",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"name", "realm_id"})}
)
public class Scope {

    @Id
    @SequenceGenerator(
            name = "scope_seq",
            sequenceName = "scope_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "scope_seq"
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

    @ManyToOne(targetEntity = Realm.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id")
    private Realm realm;

    @ManyToMany(mappedBy = "scopes", fetch = FetchType.LAZY)
    private List<ResourceServer> resourceServers;

    @ManyToMany(mappedBy = "scopes", fetch = FetchType.LAZY)
    private List<Policy> policies;
}
