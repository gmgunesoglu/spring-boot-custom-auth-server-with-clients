package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.realm.RealmDetailDto;
import com.gokhan.authserver.dto.realm.RealmDto;
import com.gokhan.authserver.dto.realm.RealmRegisterDto;
import com.gokhan.authserver.dto.realm.RealmUpdateDto;

import java.util.List;

public interface RealmService {
    
    RealmDetailDto add(RealmRegisterDto realmRegisterDto);

    List<RealmDto> getAll();

    RealmDetailDto get(Long id);

    RealmDetailDto update(Long id, RealmUpdateDto realmUpdateDto);

    RealmDetailDto delete(String id);
}
