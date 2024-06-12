package com.gokhan.authserver.config;

import com.gokhan.authserver.dto.client.ClientRegisterDto;
import com.gokhan.authserver.entity.*;
import com.gokhan.authserver.repository.*;
import com.gokhan.authserver.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.hibernate.engine.jdbc.spi.SqlExceptionHelper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.oauth2.server.authorization.settings.ClientSettings;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RealmRepository realmRepository;
    private final MyLogger myLogger;
    private final UserRolesRepository userRolesRepository;
    private final ClientRepository clientRepository;
    private final ClientSettings clientSettings;


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
            realm = realmRepository.save(realm);

            Role role = Role.builder()
                    .name("SUPER_USER")
                    .realm(realm)
                    .build();
            role = roleRepository.save(role);

//            List<AuthorizationGrantType> authorizationGrantTypes = new ArrayList<>();
//            authorizationGrantTypes.add(AuthorizationGrantType.AUTHORIZATION_CODE);
//            authorizationGrantTypes.add(AuthorizationGrantType.REFRESH_TOKEN);
//            authorizationGrantTypes.add(AuthorizationGrantType.CLIENT_CREDENTIALS);
            Client client = Client.builder()
                    .clientIdIssuedAt(new Date())
                    .baseUrl("http://127.0.0.1:4200")
                    .clientSecret(passwordEncoder.encode("secret"))
                    .name("client")
                    .clientAuthenticationMethods("client_secret_basic")
                    .authorizationGrantTypes("authorization_code,refresh_token,client_credentials,refresh_token")
                    .redirectUris("http://127.0.0.1:4200/authorized")
                    .postLogoutRedirectUris("http://127.0.0.1:4200/logout")
                    .scopes("openid,profile")
                    .clientSettings(clientSettings.toString())
                    .tokenSettings("AbstractSettings {settings={settings.token.reuse-refresh-tokens=true, settings.token.id-token-signature-algorithm=RS256, settings.token.access-token-time-to-live=PT5M, settings.token.access-token-format=org.springframework.security.oauth2.server.authorization.settings.OAuth2TokenFormat@face4f32, settings.token.refresh-token-time-to-live=PT1H, settings.token.authorization-code-time-to-live=PT5M, settings.token.device-code-time-to-live=PT5M}}")
                    .realm(realm)
                    .build();
            clientRepository.save(client);


            // Base Rows Added

//            // super user
//            User user = userRepository.save(User.builder()
//                            .username("superuser")
//                            .password(passwordEncoder.encode("superuser"))
//                            .enabled(true)
//                            .accountLocked(false)
//                            .build());
//            userRolesRepository.save(UserRoles.builder().role(role).user(user).build());

        } catch (DataIntegrityViolationException e) {
            myLogger.error("Expected Error: " + e.getMessage());
        }
    }
}
