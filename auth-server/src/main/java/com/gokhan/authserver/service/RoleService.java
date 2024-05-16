package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.role.RoleDetailDto;
import com.gokhan.authserver.dto.role.RoleDto;
import com.gokhan.authserver.dto.role.RoleRegisterDto;
import com.gokhan.authserver.dto.role.RoleUpdateDto;

import java.util.List;

public interface RoleService {

    RoleDetailDto add(RoleRegisterDto roleRegisterDto);

    List<RoleDto> getAll();

    RoleDetailDto get(Long id);

    RoleDetailDto update(Long id, RoleUpdateDto roleUpdateDto);

    RoleDetailDto delete(Long id);
}
