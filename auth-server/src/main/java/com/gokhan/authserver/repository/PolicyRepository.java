package com.gokhan.authserver.repository;

import com.gokhan.authserver.entity.Policy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PolicyRepository extends JpaRepository<Policy,Long> {
}
