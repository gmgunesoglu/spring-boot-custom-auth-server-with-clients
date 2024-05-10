package com.gokhan.authserver.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "policy_scopes")
public class PolicyScopes {

    @Id
    @Column(name = "policies_id")
    private Long policyId;

    @Id
    @Column(name = "scopes_id")
    private Long scopeId;
}


