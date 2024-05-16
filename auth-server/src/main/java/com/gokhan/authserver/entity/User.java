package com.gokhan.authserver.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(
        name = "users",
        uniqueConstraints = {@UniqueConstraint(columnNames = {"username", "client_id"})}
)
@JsonSerialize
public class User implements UserDetails, Principal {

    @Id
    @SequenceGenerator(
            name = "user_seq",
            sequenceName = "user_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_seq"
    )
    @Column(
            name = "id",
            updatable = false
    )
    private Long id;

    @Column(
            name = "username",
            nullable = false,
            length = 50,
            unique = true,
            updatable = false
    )
    private String username;

    @Column(
            name = "password",
            nullable = false,
            length = 100
    )
    private String password;

//    @Column(
//            name = "client_id"
//    )
//    private Long clientId;

    @Column(
            name = "enabled",
            nullable = false
    )
    private boolean enabled;

    @Column(
            name = "account_locked",
            nullable = false
    )
    private boolean accountLocked;


    @Override
    public String getName() {
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(
                r -> new SimpleGrantedAuthority(r.getName())
        ).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    private List<Role> roles;

    @OneToOne(mappedBy = "superUser")
    private Realm realm;

    @ManyToOne(targetEntity = Client.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "client_id", referencedColumnName = "id")
    private Client client;
}
