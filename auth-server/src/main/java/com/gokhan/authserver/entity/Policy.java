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
        name = "policy",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"uri", "method"})}
)
public class Policy {

    @Id
    @SequenceGenerator(
            name = "policy_seq",
            sequenceName = "policy_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "policy_seq"
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
            name = "base_url_id",
            nullable = false
    )
    private Long baseUrlId;

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