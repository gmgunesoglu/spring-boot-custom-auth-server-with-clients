package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.policy.PolicyDetailDto;
import com.gokhan.authserver.dto.policy.PolicyDto;
import com.gokhan.authserver.dto.policy.PolicyRegisterDto;
import com.gokhan.authserver.dto.policy.PolicyUpdateDto;
import com.gokhan.authserver.entity.*;
import com.gokhan.authserver.exceptionhandling.GlobalRuntimeException;
import com.gokhan.authserver.repository.*;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PolicyServiceImpl implements PolicyService {

     private final ResourceServerRepository resourceServerRepository;
     private final ClientRepository clientRepository;
     private final PolicyRolesRepository policyRolesRepository;
     private final RoleRepository roleRepository;
     private final PolicyRepository policyRepository;

     @Override
     @Transactional
     public PolicyDetailDto add(PolicyRegisterDto registerDto) {
          Client client = clientRepository.findByName(registerDto.getClientName()).orElseThrow(
                  () -> new UsernameNotFoundException("Client not found with name: " + registerDto.getClientName())
          );
          ResourceServer resourceServer = resourceServerRepository
                  .findByNameAndClient(registerDto.getResourceServerName(), client).orElseThrow(
                          () -> new UsernameNotFoundException("ResourceServer not found with name: " + registerDto.getResourceServerName())
                  );
          if(registerDto.getRoles() == null || registerDto.getRoles().isEmpty()) {
               throw new GlobalRuntimeException("Roles can't be empty", HttpStatus.BAD_REQUEST);
          }
          Policy policy = Policy.builder()
                  .uri(registerDto.getUri())
                  .methodType(registerDto.getMethod())
                  .resourceServer(resourceServer)
                  .build();
          policy = policyRepository.save(policy);
          List<Role> roles = new ArrayList<>();
//          List<PolicyRoles> policyRolesList = new ArrayList<>();
          for (String roleName : registerDto.getRoles()) {
               Role role = roleRepository.findByNameAndRealm(roleName, client.getRealm()).orElseThrow(
                       () -> new UsernameNotFoundException("Role not found with name: " + roleName)
               );
               policyRolesRepository.save(PolicyRoles.builder()
                       .policy(policy)
                       .role(role)
                       .build());
//               policy.getRoles().add(role);
               roles.add(role);
          }
          policy.setRoles(roles);
          PolicyDetailDto dto =  policyToPolicyDetailDto(policy);
          policy.setRoles(null);
          return dto;
     }

     @Override
     public List<PolicyDto> getAll() {
          List<PolicyDto> policyDtoList = new ArrayList<>();
          policyRepository.findAll().forEach(policy -> {
               List<String> roles = new ArrayList<>();
               for (Role role : policy.getRoles()) {
                    roles.add(role.getName());
               }
               policyDtoList.add(PolicyDto.builder()
                               .clientName(policy.getResourceServer().getClient().getName())
                               .resourceServerName(policy.getResourceServer().getName())
                               .baseUrl(policy.getResourceServer().getBaseUrl())
                               .uri(policy.getUri())
                               .method(policy.getMethodType())
                               .roles(roles)
                       .build());
          });
          return policyDtoList;
     }

     @Override
     public PolicyDetailDto get(Long id) {
          Policy policy = policyRepository.findById(id).orElseThrow(
                  () -> new UsernameNotFoundException("Policy not found with id: " + id)
          );
          return policyToPolicyDetailDto(policy);
     }

     @Override
     public PolicyDetailDto update(Long id, PolicyUpdateDto policyUpdateDto) {
          Policy policy = policyRepository.findById(id).orElseThrow(
                  () -> new UsernameNotFoundException("Policy not found with id: " + id)
          );
          if (policyUpdateDto.getUri() != null && !policyUpdateDto.getUri().isEmpty()) {
               policy.setUri(policyUpdateDto.getUri());
          }
          if (policy.getMethodType() != null){
               policy.setMethodType(policyUpdateDto.getMethod());
          }
          if (policyUpdateDto.getRoles() != null && !policyUpdateDto.getRoles().isEmpty()) {
               policyRolesRepository.deleteAll(policyRolesRepository.findByPolicyId(policy.getId()));
               Realm realm = policy.getResourceServer().getClient().getRealm();
               for (String roleName : policyUpdateDto.getRoles()) {
                    Role role = roleRepository.findByNameAndRealm(roleName, realm).orElseThrow(
                            () -> new UsernameNotFoundException("Role not found with name: " + roleName)
                    );
                    policyRolesRepository.save(PolicyRoles.builder()
                                    .role(role)
                                    .policy(policy)
                            .build());
               }
          }
          return policyToPolicyDetailDto(policyRepository.save(policy));
     }

     @Override
     public PolicyDetailDto delete(Long id) {
          return null;
     }

     private PolicyDetailDto policyToPolicyDetailDto(Policy policy) {
          List<String> roles = new ArrayList<>();
          for (Role role : policy.getRoles()) {
               roles.add(role.getName());
          }
          return PolicyDetailDto.builder()
                  .clientName(policy.getResourceServer().getClient().getName())
                  .resourceServerName(policy.getResourceServer().getName())
                  .baseUrl(policy.getResourceServer().getBaseUrl())
                  .uri(policy.getUri())
                  .method(policy.getMethodType())
                  .roles(roles)
                  .build();
     }
}
