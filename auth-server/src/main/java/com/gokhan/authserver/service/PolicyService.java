package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.policy.PolicyDetailDto;
import com.gokhan.authserver.dto.policy.PolicyDto;
import com.gokhan.authserver.dto.policy.PolicyRegisterDto;
import com.gokhan.authserver.dto.policy.PolicyUpdateDto;

import java.util.List;

public interface PolicyService {

    PolicyDetailDto add(PolicyRegisterDto registerDto);

    List<PolicyDto> getAll();

    PolicyDetailDto get(Long id);

    PolicyDetailDto update(Long id, PolicyUpdateDto policyUpdateDto);

    PolicyDetailDto delete(Long id);
}
