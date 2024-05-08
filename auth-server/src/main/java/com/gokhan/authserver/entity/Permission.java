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
        name = "permission",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"uri", "method"})}
)
public class Permission {

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
            name = "realm_id",
            nullable = false
    )
    private Long realmId;

    @Column(
            name = "path",
            nullable = false
    )
    private String path;

    @Column(
            name = "uri",
            nullable = false
    )
    private String uri;

    @Column(
            name = "method",
            nullable = false
    )
    private String method;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Scope> scopes;
}
