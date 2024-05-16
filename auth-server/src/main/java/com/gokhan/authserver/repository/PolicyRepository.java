package com.gokhan.authserver.repository;

import com.gokhan.authserver.entity.Policy;
import com.gokhan.authserver.entity.ResourceServer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PolicyRepository extends JpaRepository<Policy,Long> {

    List<Policy> findByResourceServer(ResourceServer resourceServer);
}
