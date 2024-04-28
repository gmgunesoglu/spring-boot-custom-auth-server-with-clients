package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.UserRegisterDto;
import com.gokhan.authserver.dto.UserRole;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.entity.User;
import com.gokhan.authserver.repository.RoleRepository;
import com.gokhan.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Override
    public User add(UserRegisterDto userRegisterDto) {
        User user = new User();
        user.setUsername(userRegisterDto.getUsername());
        user.setPassword(passwordEncoder.encode(userRegisterDto.getPassword()));
        user.setEnabled(true);
        user.setAccountLocked(false);

        // Role nesnelerini oluşturup kaydetme
        List<Role> roles = new ArrayList<>();
        for(UserRole userRole : userRegisterDto.getRoles()) {
            Role role = roleRepository.findByName(userRole.toString()).orElseThrow(
                    () -> new UsernameNotFoundException("Role " + userRole.toString() + " not found")
            );
            roles.add(role);
        }
        user.setRoles(roles);

        user = userRepository.save(user);
        return user;
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id: " + id)
        );
    }

    @Override
    public User update(Long id, User user) {
        return null;
    }

    @Override
    public User delete(Long id) {
        return null;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found with username: " + username)
        );
    }
}
