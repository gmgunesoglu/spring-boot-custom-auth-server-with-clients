package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.client.ClientDetailDto;
import com.gokhan.authserver.dto.client.ClientDto;
import com.gokhan.authserver.dto.client.ClientRegisterDto;
import com.gokhan.authserver.dto.client.ClientUpdateDto;
import com.gokhan.authserver.entity.Client;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;

import java.util.List;

public interface ClientService extends RegisteredClientRepository {

    ClientDetailDto add(ClientRegisterDto clientRegisterDto);

    List<ClientDto> getAll();

    Client findById(Long id);

    ClientDetailDto findClientByName(String name);

    ClientDetailDto update(String name, ClientUpdateDto clientUpdateDto);

    ClientDetailDto delete(String clientId);
}
