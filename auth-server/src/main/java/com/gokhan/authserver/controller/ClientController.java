package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.client.ClientDetailDto;
import com.gokhan.authserver.dto.client.ClientDto;
import com.gokhan.authserver.dto.client.ClientRegisterDto;
import com.gokhan.authserver.dto.client.ClientUpdateDto;
import com.gokhan.authserver.entity.Client;
import com.gokhan.authserver.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    @PostMapping
    public ClientDetailDto add(@RequestBody ClientRegisterDto clientRegisterDto) {
        return clientService.add(clientRegisterDto);
    }

    @GetMapping
    public List<ClientDto> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{name}")
    public ClientDetailDto get(@PathVariable String name) {
        return clientService.findClientByName(name);
    }

    @PutMapping("/{name}")
    public ClientDetailDto update(@PathVariable String name, @RequestBody ClientUpdateDto clientUpdateDto) {
        return clientService.update(name, clientUpdateDto);
    }

    // TODO -> silme işlemleri ile ilgili iş lojiği henüz düşünülmedi
    @DeleteMapping("/{clientId}")
    public ClientDetailDto delete(@PathVariable String clientId) {
        return clientService.delete(clientId);
    }
}
