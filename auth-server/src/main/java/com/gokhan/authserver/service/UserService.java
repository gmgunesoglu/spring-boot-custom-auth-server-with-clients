package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.UserRegisterDto;
import com.gokhan.authserver.entity.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {

    User add(UserRegisterDto userRegisterDto);

    List<User> getAll();

    User get(Long id);

    User update(Long id, User user);

    User delete(Long id);
}
