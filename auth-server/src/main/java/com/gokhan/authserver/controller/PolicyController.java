package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.policy.PolicyDetailDto;
import com.gokhan.authserver.dto.policy.PolicyDto;
import com.gokhan.authserver.dto.policy.PolicyRegisterDto;
import com.gokhan.authserver.dto.policy.PolicyUpdateDto;
import com.gokhan.authserver.service.PolicyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/policies")
@RequiredArgsConstructor
public class PolicyController {

    private final PolicyService policyService;

    @PostMapping
    public PolicyDetailDto add(@RequestBody PolicyRegisterDto registerDto) {
        return policyService.add(registerDto);
    }

    @GetMapping
    public List<PolicyDto> getAll() {
        return policyService.getAll();
    }

    @GetMapping("/{id}")
    public PolicyDetailDto get(@PathVariable Long id) {
        return policyService.get(id);
    }

    @PutMapping("/{id}")
    public PolicyDetailDto update(@PathVariable Long id, @RequestBody PolicyUpdateDto policyUpdateDto) {
        return policyService.update(id, policyUpdateDto);
    }

    // TODO -> silme işlemleri ile ilgili iş lojiği henüz düşünülmedi
    @DeleteMapping("/{id}")
    public PolicyDetailDto delete(@PathVariable Long id) {
        return policyService.delete(id);
    }
}
