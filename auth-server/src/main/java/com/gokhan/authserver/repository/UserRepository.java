package com.gokhan.authserver.repository;

import com.gokhan.authserver.dto.user.UserDto;
import com.gokhan.authserver.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Query("SELECT new com.gokhan.authserver.dto.user.UserDto" +
            "(u.username, u.realm.name, u.client.name, u.accountLocked) FROM User u")
    List<UserDto> listUserDto();

    @Query("SELECT new com.gokhan.authserver.dto.user.UserDto" +
            "(u.username, u.realm.name, u.client.name, u.accountLocked) FROM User u " +
            "WHERE u.username LIKE :username")
    List<UserDto> findAllByUsername(String username);

    List<User> findAllByUsernameStartingWith(String usernameStartingWith);
}
