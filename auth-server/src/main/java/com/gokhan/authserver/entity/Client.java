package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "client")
public class Client implements Serializable {

    @Id
    @SequenceGenerator(
            name = "client_seq",
            sequenceName = "client_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "client_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

//    @Column(
//            name = "realm_id",
//            nullable = false,
//            updatable = false
//    )
//    private Long realmId;

//    @Column(
//            name = "client_id",
//            nullable = false,
//            updatable = false,
//            length = 100
//    )
//    private String clientId;

    @Column(
            name = "client_id_issued_at",
            nullable = false
    )
    private Date clientIdIssuedAt;

    @Column(
            name = "base_url",
            nullable = false,
            unique = true
    )
    private String baseUrl;

    @Column(
            name = "client_secret",
            nullable = false,
            length = 200
    )
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Date clientSecretExpiresAt;

    @Column(
            name = "name",
            nullable = false,
            length = 50,
            unique = true
    )
    private String name;

    @Column(
            name = "client_authentication_methods",
            nullable = false,
            length = 1000
    )
    private String clientAuthenticationMethods;

    @Column(
            name = "authorization_grant_types",
            nullable = false,
            length = 1000
    )
    private String authorizationGrantTypes;

    @Column(
            name = "redirect_uris",
            nullable = false,
            length = 1000
    )
    private String redirectUris;

    @Column(
            name = "post_logout_redirect_uris",
            nullable = false,
            length = 1000
    )
    private String postLogoutRedirectUris;

    @Column(
            name = "scopes",
            nullable = false,
            length = 100
    )
    private String scopes;

    @Column(
            name = "client_settings",
            nullable = false,
            length = 2000
    )
    private String clientSettings;

    @Column(
            name = "access_token_duration",
            nullable = false,
            length = 10
    )
    private String accessTokenDuration;

    @Column(
            name = "refresh_token_duration",
            nullable = false,
            length = 10
    )
    private String refreshTokenDuration;

    @Column(
            name = "authorization_code_duration",
            nullable = false,
            length = 10
    )
    private String authorizationCodeDuration;

    @OneToOne(targetEntity = Realm.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "realm_id", referencedColumnName = "id", updatable = false, unique = true)
    private Realm realm;

    @OneToMany(targetEntity = ResourceServer.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private List<ResourceServer> resourceServers;

    @OneToMany(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private List<User> users;
}
