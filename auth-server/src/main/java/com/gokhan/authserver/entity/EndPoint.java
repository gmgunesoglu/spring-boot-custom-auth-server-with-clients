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
        name = "end_point",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"resource_server_id", "uri"})}
)
public class EndPoint {

    @Id
    @SequenceGenerator(
            name = "end_point_seq",
            sequenceName = "end_point_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "end_point_seq"
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

    @ManyToOne(targetEntity = ResourceServer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "resource_server_id")
    private ResourceServer resourceServer;

    @OneToMany(targetEntity = Policy.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "end_point_id", referencedColumnName = "id")
    private List<Policy> policies;
}
