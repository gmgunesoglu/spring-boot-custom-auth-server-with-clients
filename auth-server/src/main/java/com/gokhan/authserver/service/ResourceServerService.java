package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.resource_server.ResourceServerDetailDto;
import com.gokhan.authserver.dto.resource_server.ResourceServerDto;
import com.gokhan.authserver.dto.resource_server.ResourceServerRegisterDto;
import com.gokhan.authserver.dto.resource_server.ResourceServerUpdateDto;

import java.util.List;

public interface ResourceServerService {

    ResourceServerDetailDto add(ResourceServerRegisterDto resourceServerRegisterDto);

    List<ResourceServerDto> getAll();

    ResourceServerDetailDto get(Long id);

    ResourceServerDetailDto update(Long id, ResourceServerUpdateDto resourceServerUpdateDto);

    ResourceServerDetailDto delete(Long id);
}
