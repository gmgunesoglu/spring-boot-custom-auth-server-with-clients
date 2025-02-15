package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "policy_roles",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"policies_id", "roles_id"})}
)
public class PolicyRoles {

    @Id
    @SequenceGenerator(
            name = "policy_roles_seq",
            sequenceName = "policy_roles_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "policy_roles_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne(targetEntity = Policy.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "policies_id", referencedColumnName = "id")
    private Policy policy;

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    private Role role;
}
