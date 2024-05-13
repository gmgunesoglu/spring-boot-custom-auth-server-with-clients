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
}
