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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"end_point_id", "policy_scopes_id", "policy_methods_id"})}
)
public class    Policy {

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

//    @Column(
//            name = "realm_id",
//            nullable = false
//    )
//    private Long realmId;

//    @Column(
//            name = "end_point_id",
//            nullable = false
//    )
//    private Long endPointId;

    @Column(
            name = "policy_scopes_id",
            nullable = false
    )
    private Long policyScopesId;

    @Column(
            name = "policy_methods_id",
            nullable = false
    )
    private Long policyMethodsId;

    @ManyToOne(targetEntity = Realm.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id")
    private Realm realm;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Scope> scopes;

    @ManyToOne(targetEntity = EndPoint.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "end_point_id")
    private EndPoint endPoint;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;

}
