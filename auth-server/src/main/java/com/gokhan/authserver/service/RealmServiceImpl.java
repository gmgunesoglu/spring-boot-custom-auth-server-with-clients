package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.realm.RealmDetailDto;
import com.gokhan.authserver.dto.realm.RealmDto;
import com.gokhan.authserver.dto.realm.RealmRegisterDto;
import com.gokhan.authserver.dto.realm.RealmUpdateDto;
import com.gokhan.authserver.entity.Realm;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.repository.RealmRepository;
import com.gokhan.authserver.repository.ResourceServerRepository;
import com.gokhan.authserver.repository.RoleRepository;
import com.gokhan.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RealmServiceImpl implements RealmService {

    private final RealmRepository realmRepository;
    private final UserRepository userRepository;
    private final ResourceServerRepository resourceServerRepository;
    private final RoleRepository roleRepository;

    @Override
    public RealmDetailDto add(RealmRegisterDto realmRegisterDto) {
        Realm realm = Realm.builder()
                .name(realmRegisterDto.getName())
                .superUser(userRepository.findByUsername(realmRegisterDto.getSuperUserName()).orElseThrow(
                        () -> new UsernameNotFoundException("Super user not found with username: " + realmRegisterDto.getSuperUserName())
                ))
                .build();
        realm = realmRepository.save(realm);
        return realmToRealDetailDto(realm);
    }

    @Override
    public List<RealmDto> getAll() {
        List<Realm> realms = realmRepository.findAll();
        List<RealmDto> realmDtoList = new ArrayList<>();
        for (Realm realm : realms) {
            realmDtoList.add(
                    RealmDto.builder()
                            .realName(realm.getName())
                            .superUserName(realm.getSuperUser().getName())
                            .clientName(realm.getClient() != null ?
                                    realm.getClient().getName() : null)
                            .rolesName(roleRepository.listRoleNameByRealmId(realm.getId()))
                            .build()
            );
        }
        return realmDtoList;
    }

    @Override
    public RealmDetailDto get(Long id) {
        Realm realm = realmRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Realm not found with id: " + id)
        );
        return realmToRealDetailDto(realm);
    }

    @Override
    public RealmDetailDto update(Long id, RealmUpdateDto realmUpdateDto) {
        Realm realm = realmRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Realm not found with id: " + id)
        );
        realm.setName(realmUpdateDto.getName());
        realm = realmRepository.save(realm);
        return realmToRealDetailDto(realm);
    }

    @Override
    public RealmDetailDto delete(String id) {
        return null;
    }

    private RealmDetailDto realmToRealDetailDto(Realm realm) {
        List<String> rolesName = new ArrayList<>();
        if (realm.getRoles() != null) {
            for (Role role : realm.getRoles()) {
                rolesName.add(role.getName());
            }
        }
        List<String> resourceServersName = new ArrayList<>();
        String clientName = null;
        if(realm.getClient() != null) {
            clientName = realm.getClient().getName();
            resourceServersName = resourceServerRepository.listNamesByClientId(realm.getClient().getId());
        }
        return RealmDetailDto.builder()
                .realmName(realm.getName())
                .superUserName(realm.getSuperUser().getName())
                .clientName(clientName)
                .rolesName(rolesName)
                .resourceServersName(resourceServersName)
                .build();
    }
}
