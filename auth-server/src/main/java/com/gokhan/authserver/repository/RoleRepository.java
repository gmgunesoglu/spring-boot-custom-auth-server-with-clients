package com.gokhan.authserver.repository;

import com.gokhan.authserver.dto.role.RoleDto;
import com.gokhan.authserver.entity.Realm;
import com.gokhan.authserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Optional<Role> findByName(String name);
    Optional<Role> findByNameAndRealm(String name, Realm realm);

    @Query("SELECT new com.gokhan.authserver.dto.role.RoleDto(r.name, r.realm.name) " +
            "FROM Role r")
    List<RoleDto> listRoleDto();

    @Query("SELECT new com.gokhan.authserver.dto.role.RoleDto(r.name, r.realm.name) " +
            "FROM Role r WHERE r.id = :id")
    Optional<RoleDto> findRoleDtoById(Long id);

    @Query("SELECT r.name FROM Role r WHERE r.realm.id = :realmId")
    List<String> listRoleNameByRealmId(Long realmId);
}
