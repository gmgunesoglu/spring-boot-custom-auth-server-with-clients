package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.ClientRegisterDto;
import com.gokhan.authserver.entity.Client;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.List;

public interface ClientService extends RegisteredClientRepository {
    Client add(ClientRegisterDto clientRegisterDto);

    Client findById(Long id);

    List<Client> getAll();

    Client findClientByClientId(String id);
}
