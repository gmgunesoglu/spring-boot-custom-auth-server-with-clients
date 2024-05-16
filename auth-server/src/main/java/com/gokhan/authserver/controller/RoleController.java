package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.role.RoleDetailDto;
import com.gokhan.authserver.dto.role.RoleDto;
import com.gokhan.authserver.dto.role.RoleRegisterDto;
import com.gokhan.authserver.dto.role.RoleUpdateDto;
import com.gokhan.authserver.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @PostMapping
    public RoleDetailDto add(@RequestBody RoleRegisterDto roleRegisterDto){
        return roleService.add(roleRegisterDto);
    }

    @GetMapping
    public List<RoleDto> getAll(){
        return roleService.getAll();
    }

    @GetMapping("/{id}")
    public RoleDetailDto get(@PathVariable Long id){
        return roleService.get(id);
    }

    @PutMapping("/{id}")
    public RoleDetailDto update(@PathVariable Long id, @RequestBody RoleUpdateDto roleUpdateDto){
        return roleService.update(id, roleUpdateDto);
    }

    // TODO -> silme işlemleri ile ilgili iş lojiği henüz düşünülmedi
    @DeleteMapping("/{id}")
    public RoleDetailDto delete(@PathVariable Long id){
        return roleService.delete(id);
    }
}
