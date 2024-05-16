package com.gokhan.authserver.repository;

import com.gokhan.authserver.dto.realm.RealmDto;
import com.gokhan.authserver.entity.Realm;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RealmRepository extends JpaRepository<Realm,Long> {

    Optional<Realm> findByName(String realmName);

    @Query("SELECT new com.gokhan.authserver.dto.realm.RealmDto" +
            "(r.name, r.superUser.username, r.client.name) FROM Realm r")
    List<RealmDto> listRealmDto();
}
