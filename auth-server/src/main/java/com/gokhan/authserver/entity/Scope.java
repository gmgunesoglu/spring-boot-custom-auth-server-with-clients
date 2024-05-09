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
            length = 50
    )
    private String name;

    @Column(
            name = "realm_id",
            nullable = false
    )
    private Long realmId;

    @ManyToMany(mappedBy = "scopes", fetch = FetchType.LAZY)
    private List<Policy> policies;
}
