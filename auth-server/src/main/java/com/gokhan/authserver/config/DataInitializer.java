package com.gokhan.authserver.config;

import com.gokhan.authserver.entity.Realm;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.entity.User;
import com.gokhan.authserver.repository.RealmRepository;
import com.gokhan.authserver.repository.RoleRepository;
import com.gokhan.authserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RealmRepository realmRepository;
    private final MyLogger myLogger;

    @Override
    public void run(String... args) {
        try {
            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin"))
                    .enabled(true)
                    .accountLocked(false)
                    .build();
            admin = userRepository.save(admin);

            Realm realm = Realm.builder()
                    .superUser(admin)
                    .name("realm")
                    .build();
            realmRepository.save(realm);

            Role role = Role.builder()
                    .name("SUPER_USER")
                    .realm(realm)
                    .build();
            roleRepository.save(role);
        } catch (DataIntegrityViolationException e) {
            myLogger.error("Expected Error: " + e.getMessage());
        }
    }
}
