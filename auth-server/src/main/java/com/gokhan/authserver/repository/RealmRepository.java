package com.gokhan.authserver.repository;

import com.gokhan.authserver.entity.Realm;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RealmRepository extends JpaRepository<Realm,Long> {
}
