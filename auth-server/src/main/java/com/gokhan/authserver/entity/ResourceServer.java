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
        name = "resource_server",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"client_id", "name"})}
)
public class ResourceServer {

    @Id
    @SequenceGenerator(
            name = "resource_server_seq",
            sequenceName = "resource_server_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "resource_server_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

//    @Column(
//            name = "client_id",
//            nullable = false
//    )
//    private Long clientId;

    @Column(
            name = "name",
            nullable = false,
            length = 30
    )
    private String name;

    @Column(
            name = "base_url",
            nullable = false,
            unique = true
    )
    private String baseUrl;

//    @ManyToOne(targetEntity = Realm.class, fetch = FetchType.LAZY)
//    @JoinColumn(name = "realm_id")
//    private Realm realm;
//
//    @ManyToMany(fetch = FetchType.LAZY)
//    private List<Scope> scopes;
//
    @OneToMany(targetEntity = Policy.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_server_id", referencedColumnName = "id")
    private List<Policy> policies;

    @ManyToOne(targetEntity = Client.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id")
    private Client client;
}
