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
        uniqueConstraints = {@UniqueConstraint(columnNames = {"resource_server_id", "uri", "method_type"})}
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
//            name = "resource_server_id",
//            nullable = false
//    )
//    private Long resourceServerId;

    @Column(
            name = "uri",
            nullable = false
    )
    private String uri;

    @Column(
            name = "method_type",
            nullable = false
    )
    private MethodType methodType;

//    @ManyToOne(targetEntity = Realm.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "realm_id")
//    private Realm realm;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Scope> scopes;
//
//    @ManyToOne(targetEntity = EndPoint.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "end_point_id")
//    private EndPoint endPoint;

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;

    @ManyToOne(targetEntity = ResourceServer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_server_id")
    private ResourceServer resourceServer;

}
