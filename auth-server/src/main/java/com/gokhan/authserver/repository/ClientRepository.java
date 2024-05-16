package com.gokhan.authserver.repository;

import com.gokhan.authserver.dto.client.ClientDto;
import com.gokhan.authserver.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.oauth2.server.authorization.client.JdbcRegisteredClientRepository;

import java.util.List;
import java.util.Optional;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Optional<Client> findByName(String name);

    @Query("SELECT new com.gokhan.authserver.dto.client.ClientDto" +
            "(c.realm.superUser.username, c.realm.name, c.name) FROM Client c")
    List<ClientDto> listClientDto();
}
