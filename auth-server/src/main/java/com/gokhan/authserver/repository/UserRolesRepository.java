package com.gokhan.authserver.repository;

import com.gokhan.authserver.entity.UserRoles;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {

    List<UserRoles> findByUserId(Long userId);

    Optional<UserRoles> findByUserIdAndRoleId(Long userId, Long roleId);


//    @Transactional
//    @Modifying
//    @Query("DELETE FROM UserRoles ur WHERE ur.user.id = :userId AND ur.role.id = :roleId")
//    void deleteUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleId);
}
