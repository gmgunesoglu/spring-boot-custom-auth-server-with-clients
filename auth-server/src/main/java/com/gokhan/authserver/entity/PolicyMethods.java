package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Immutable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "policy_methods",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"id", "method_id"})}
)
@Immutable
public class PolicyMethods {

    @Id
    @SequenceGenerator(
            name = "policy_methods_seq",
            sequenceName = "policy_methods_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "policy_methods_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "method_id",
            nullable = false
    )
    private Long methodId;
}
