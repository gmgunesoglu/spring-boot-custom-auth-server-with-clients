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
        name = "users_roles",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"users_id", "roles_id"})}
)
public class UserRoles {

    @Id
    @SequenceGenerator(
            name = "users_roles_seq",
            sequenceName = "users_roles_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "users_roles_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "users_id", referencedColumnName = "id")
    private User user;

    @ManyToOne(targetEntity = Role.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "roles_id", referencedColumnName = "id")
    private Role role;
}
