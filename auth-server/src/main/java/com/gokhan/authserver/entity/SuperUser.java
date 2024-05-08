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
@Table(name = "super_user")
public class SuperUser {

    @Id
    @SequenceGenerator(
            name = "super_user_seq",
            sequenceName = "super_user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "super_user_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            unique = true,
            length = 50
    )
    private String username;

    @Column(
            name = "password",
            nullable = false,
            length = 100
    )
    private String password;

    @OneToMany(targetEntity = Realm.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "super_user_id", referencedColumnName = "id")
    private List<Realm> realms;
}
