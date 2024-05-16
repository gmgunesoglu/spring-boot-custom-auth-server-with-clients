package com.gokhan.authserver.repository;

import com.gokhan.authserver.entity.UserRoles;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRolesRepository extends JpaRepository<UserRoles,Long> {

    List<UserRoles> findByUserId(Long userId);

    @Transactional
    @Query("DELETE FROM UserRoles ur WHERE ur.user.id =: userId")
    void resetRolesOfUser(Long userId);
}
