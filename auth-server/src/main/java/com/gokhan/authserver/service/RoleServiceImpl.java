package com.gokhan.authserver.service;

import com.gokhan.authserver.config.MyLogger;
import com.gokhan.authserver.dto.role.RoleDetailDto;
import com.gokhan.authserver.dto.role.RoleDto;
import com.gokhan.authserver.dto.role.RoleRegisterDto;
import com.gokhan.authserver.dto.role.RoleUpdateDto;
import com.gokhan.authserver.entity.Realm;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.entity.User;
import com.gokhan.authserver.repository.RealmRepository;
import com.gokhan.authserver.repository.RoleRepository;
import com.gokhan.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final MyLogger myLogger;

    @Override
    public RoleDetailDto add(RoleRegisterDto roleRegisterDto) {
        User user = userRepository.findByUsername(roleRegisterDto.getSuperUserName()).orElseThrow(
                () -> new UsernameNotFoundException("Superuser not found with username: " + roleRegisterDto.getSuperUserName())
        );
        if (user.getRealm() == null){
            throw new UsernameNotFoundException("Realm not found with username: " + roleRegisterDto.getSuperUserName());
        }
        Role role = Role.builder()
                .name(roleRegisterDto.getRoleName())
                .realm(user.getRealm())
                .build();
        role = roleRepository.save(role);
        return roleToRoleDetailDto(role);
    }

    @Override
    public List<RoleDto> getAll() {
        return roleRepository.listRoleDto();
    }

    @Override
    public RoleDetailDto get(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Role not found with id: " + id)
        );
        return roleToRoleDetailDto(role);
    }

    @Override
    public RoleDetailDto update(Long id, RoleUpdateDto roleUpdateDto) {
        Role role = roleRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Role not found with id: " + id)
        );
        role.setName(roleUpdateDto.getName());
        try{
            role = roleRepository.save(role);
        }catch (DataIntegrityViolationException e){
            myLogger.error("This role name is duplicated: " + e.getMessage());
            throw new UsernameNotFoundException("Duplicate role name: " + roleUpdateDto.getName());
        }
        return roleToRoleDetailDto(role);
    }

    @Override
    public RoleDetailDto delete(Long id) {
        return null;
    }

    private RoleDetailDto roleToRoleDetailDto(Role role) {
        return RoleDetailDto.builder()
                .name(role.getName())
                .realmName(role.getRealm().getName())
                .superUserName(role.getRealm().getSuperUser().getName())
                .build();
    }

}
