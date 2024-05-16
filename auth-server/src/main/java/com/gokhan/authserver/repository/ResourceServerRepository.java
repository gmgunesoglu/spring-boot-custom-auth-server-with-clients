package com.gokhan.authserver.repository;

import com.gokhan.authserver.dto.resource_server.ResourceServerDto;
import com.gokhan.authserver.entity.Client;
import com.gokhan.authserver.entity.ResourceServer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ResourceServerRepository extends JpaRepository<ResourceServer,Long> {

    @Query("SELECT rs.name FROM ResourceServer rs WHERE rs.client.id = :clientId")
    List<String> listNamesByClientId(Long clientId);

    @Query("SELECT new com.gokhan.authserver.dto.resource_server.ResourceServerDto" +
            "(rs.client.name, rs.name, rs.baseUrl) FROM ResourceServer rs")
    List<ResourceServerDto> listResourceServerDto();

    Optional<ResourceServer> findByNameAndClient(String name, Client client);
}
