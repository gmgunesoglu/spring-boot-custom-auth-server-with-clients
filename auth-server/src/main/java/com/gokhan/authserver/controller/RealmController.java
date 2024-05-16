package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.realm.RealmDetailDto;
import com.gokhan.authserver.dto.realm.RealmDto;
import com.gokhan.authserver.dto.realm.RealmRegisterDto;
import com.gokhan.authserver.dto.realm.RealmUpdateDto;
import com.gokhan.authserver.entity.Realm;
import com.gokhan.authserver.service.RealmService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/realms")
@RequiredArgsConstructor
public class RealmController {

    private final RealmService realmService;

    @PostMapping
    public RealmDetailDto add(@RequestBody RealmRegisterDto realmRegisterDto) {
        return realmService.add(realmRegisterDto);
    }

    @GetMapping
    public List<RealmDto> getAll() {
        return realmService.getAll();
    }

    @GetMapping("/{id}")
    public RealmDetailDto get(@PathVariable Long id) {
        return realmService.get(id);
    }

    @PutMapping("/{id}")
    public RealmDetailDto update(@PathVariable Long id, @RequestBody RealmUpdateDto realmUpdateDto) {
        return realmService.update(id, realmUpdateDto);
    }

    // TODO -> silme işlemleri ile ilgili iş lojiği henüz düşünülmedi
    @DeleteMapping("/{id}")
    public RealmDetailDto delete(@PathVariable String id) {
        return realmService.delete(id);
    }

    @GetMapping("/test")
    public String test() {
        return "test ok for open end point";
    }


}
