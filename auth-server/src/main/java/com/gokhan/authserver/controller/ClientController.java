package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.ClientRegisterDto;
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
    public Client add(@RequestBody ClientRegisterDto clientRegisterDto) {
        return clientService.add(clientRegisterDto);
    }

    @GetMapping
    public List<Client> getAll() {
        return clientService.getAll();
    }

    @GetMapping("/{id}")
    public Client get(@PathVariable String id) {
        return clientService.findClientByClientId(id);
    }
}
