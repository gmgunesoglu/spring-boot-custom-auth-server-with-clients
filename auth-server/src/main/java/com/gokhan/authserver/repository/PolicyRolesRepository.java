package com.gokhan.authserver.repository;

import com.gokhan.authserver.entity.PolicyRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRolesRepository extends JpaRepository<PolicyRoles,Long> {

    List<PolicyRoles> findByPolicyId(Long policyId);
}
