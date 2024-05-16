package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.resource_server.ResourceServerDetailDto;
import com.gokhan.authserver.dto.resource_server.ResourceServerDto;
import com.gokhan.authserver.dto.resource_server.ResourceServerRegisterDto;
import com.gokhan.authserver.dto.resource_server.ResourceServerUpdateDto;
import com.gokhan.authserver.service.ResourceServerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/resource-servers")
@RequiredArgsConstructor
public class ResourceServerController {

    private final ResourceServerService resourceServerService;

    @PostMapping
    public ResourceServerDetailDto add(@RequestBody ResourceServerRegisterDto resourceServerRegisterDto) {
        return resourceServerService.add(resourceServerRegisterDto);
    }

    @GetMapping
    public List<ResourceServerDto> getAll() {
        return resourceServerService.getAll();
    }

    @GetMapping("/{id}")
    public ResourceServerDetailDto get(@PathVariable Long id) {
        return resourceServerService.get(id);
    }

    @PutMapping("/{id}")
    public ResourceServerDetailDto update(@PathVariable Long id, @RequestBody ResourceServerUpdateDto resourceServerUpdateDto) {
        return resourceServerService.update(id, resourceServerUpdateDto);
    }

    // TODO -> silme işlemleri ile ilgili iş lojiği henüz düşünülmedi
    @DeleteMapping("/{id}")
    public ResourceServerDetailDto delete(@PathVariable Long id) {
        return resourceServerService.delete(id);
    }
}
