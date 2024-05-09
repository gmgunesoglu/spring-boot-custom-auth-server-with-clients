package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.security.oauth2.server.authorization.settings.TokenSettings;
import org.springframework.security.oauth2.server.authorization.util.SpringAuthorizationServerVersion;

import java.io.Serializable;
import java.security.Principal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


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

    @Column(
            name = "client_id",
            unique = true,
            nullable = false,
            length = 100
    )
    private String clientId;

    @Column(
            name = "client_id_issued_at",
            nullable = false
    )
    private Date clientIdIssuedAt;

    @Column(
            name = "client_secret",
            nullable = false,
            length = 200
    )
    private String clientSecret;

    @Column(name = "client_secret_expires_at")
    private Date clientSecretExpiresAt;

    @Column(
            name = "client_name",
            nullable = false,
            length = 200
    )
    private String clientName;

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
            length = 1000
    )
    private String scopes;

    @Column(
            name = "client_settings",
            nullable = false,
            length = 2000
    )
    private String clientSettings;

    @Column(
            name = "token_settings",
            nullable = false,
            length = 2000
    )
    private String tokenSettings;

    @Column(
            name = "realm_id",
            nullable = false
    )
    private Long realmId;

}
