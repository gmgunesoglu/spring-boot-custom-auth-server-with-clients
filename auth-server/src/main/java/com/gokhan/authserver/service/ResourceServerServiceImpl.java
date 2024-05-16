package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.resource_server.*;
import com.gokhan.authserver.entity.Client;
import com.gokhan.authserver.entity.Policy;
import com.gokhan.authserver.entity.ResourceServer;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.repository.ClientRepository;
import com.gokhan.authserver.repository.PolicyRepository;
import com.gokhan.authserver.repository.ResourceServerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceServerServiceImpl implements ResourceServerService {

    private final ResourceServerRepository resourceServerRepository;
    private final ClientRepository clientRepository;
    private final PolicyRepository policyRepository;

    @Override
    public ResourceServerDetailDto add(ResourceServerRegisterDto resourceServerRegisterDto) {
        Client client = clientRepository.findByName(resourceServerRegisterDto.getClientName()).orElseThrow(
                () -> new UsernameNotFoundException("Client not found with name: " + resourceServerRegisterDto.getClientName())
        );
        ResourceServer resourceServer = ResourceServer.builder()
                .name(resourceServerRegisterDto.getName())
                .baseUrl(resourceServerRegisterDto.getBaseUrl())
                .client(client)
                .build();
        return resourceServerToResourceServerDetailDto(resourceServerRepository.save(resourceServer));
    }

    @Override
    public List<ResourceServerDto> getAll() {
        return resourceServerRepository.listResourceServerDto();
    }

    @Override
    public ResourceServerDetailDto get(Long id) {
        ResourceServer resourceServer = resourceServerRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("ResourceServer not found with id: " + id)
        );
        return resourceServerToResourceServerDetailDto(resourceServer);
    }

    @Override
    public ResourceServerDetailDto update(Long id, ResourceServerUpdateDto resourceServerUpdateDto) {
        ResourceServer resourceServer = resourceServerRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("ResourceServer not found with id: " + id)
        );
        if (resourceServerUpdateDto.getBaseUrl() != null && !resourceServerUpdateDto.getBaseUrl().isEmpty()) {
            resourceServer.setBaseUrl(resourceServerUpdateDto.getBaseUrl());
        }
        if (resourceServerUpdateDto.getName() != null && !resourceServerUpdateDto.getName().isEmpty()) {
            resourceServer.setName(resourceServerUpdateDto.getName());
        }
        return resourceServerToResourceServerDetailDto(resourceServerRepository.save(resourceServer));
    }

    @Override
    public ResourceServerDetailDto delete(Long id) {
        return null;
    }

    private ResourceServerDetailDto resourceServerToResourceServerDetailDto(ResourceServer resourceServer){
        List<ResourceServerPoliciesDto> policies = new ArrayList<>();
        if(resourceServer.getPolicies() != null){
            for(Policy policy : resourceServer.getPolicies()){
                List<String> roles = new ArrayList<>();
                for(Role role : policy.getRoles()){
                    roles.add(role.getName());
                }
                policies.add(ResourceServerPoliciesDto.builder()
                        .uri(policy.getUri())
                        .method(policy.getMethodType())
                        .roles(roles)
                        .build());
            }
        }
        return ResourceServerDetailDto.builder()
                .clientName(resourceServer.getClient().getName())
                .name(resourceServer.getName())
                .baseUrl(resourceServer.getBaseUrl())
                .policies(policies)
                .build();
    }
}
