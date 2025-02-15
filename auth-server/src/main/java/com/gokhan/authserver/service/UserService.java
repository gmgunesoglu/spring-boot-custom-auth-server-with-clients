package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.user.*;
import com.gokhan.authserver.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    UserDetailDto addSuperUser(SuperUserRegisterDto superUserRegisterDto);

    UserDetailDto add(UserRegisterDto userRegisterDto);

    List<UserDto> getAll();

    List<UserDto> getAllByUsername(String username);

    UserDetailDto get(Long id);

    UserDetailDto getByUsername(String username);

    User update(Long id, User user);

    UserDetailDto setRoles(String username, UserSetRoleDto userSetRoleDto);

    UserDetailDto delete(Long id);

    User findByUsername(String username);

    UserDetailDto block(String username);

    UserDetailDto unblock(String username);
}
