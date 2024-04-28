package com.gokhan.authserver.config;

import com.gokhan.authserver.dto.UserRole;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;

    @Override
    public void run(String... args) {
        for (UserRole userRole : UserRole.values()) {
            Role role = Role.builder().name(userRole.toString()).build();
            roleRepository.save(role);
        }
    }
}
