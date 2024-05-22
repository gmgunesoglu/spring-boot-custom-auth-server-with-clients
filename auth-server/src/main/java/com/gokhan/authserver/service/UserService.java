package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.user.*;
import com.gokhan.authserver.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetailDto addSuperUser(SuperUserRegisterDto superUserRegisterDto);

    UserDetailDto add(UserRegisterDto userRegisterDto);

    List<UserDto> getAll();

    UserDetailDto get(Long id);

    User update(Long id, User user);

    UserDetailDto setRoles(Long id, UserSetRoleDto userSetRoleDto);

    UserDetailDto delete(Long id);

    User findByUsername(String username);
}
