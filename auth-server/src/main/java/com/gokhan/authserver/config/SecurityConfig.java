package com.gokhan.authserver.config;

import com.gokhan.authserver.service.ClientService;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configuration.OAuth2AuthorizationServerConfiguration;
import org.springframework.security.oauth2.server.authorization.config.annotation.web.configurers.OAuth2AuthorizationServerConfigurer;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.util.matcher.MediaTypeRequestMatcher;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.UUID;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    @Bean
    @Order(1)
    public SecurityFilterChain authorizationServerSecurityFilterChain(HttpSecurity http)
            throws Exception {
        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
                .oidc(Customizer.withDefaults());	// Enable OpenID Connect 1.0
        http
                // Redirect to the login page when not authenticated from the
                // authorization endpoint
                .exceptionHandling((exceptions) -> exceptions
                        .defaultAuthenticationEntryPointFor(
                                new LoginUrlAuthenticationEntryPoint("/login"),
                                new MediaTypeRequestMatcher(MediaType.TEXT_HTML)
                        )
                )
                // Accept access tokens for User Info and/or Client Registration
                .oauth2ResourceServer((resourceServer) -> resourceServer
                        .jwt(Customizer.withDefaults()));

        return http.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http)
            throws Exception {
        http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> req.requestMatchers(
//                        "/oauth2/**",
                        "/users/**",
                        "/clients/**"
                ).permitAll())
                .authorizeHttpRequests((authorize) -> authorize
                        .anyRequest().authenticated()
                )
                // Form login handles the redirect to the login page from the
                // authorization server filter chain
                .formLogin(Customizer.withDefaults());

        return http.build();
    }


//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req ->
//                        req.requestMatchers(
//                                        "/users/**",
//                                        "/clients"
//                                )
//                                .permitAll()
//                                .anyRequest()
//                                .authenticated()
//                );
//
//        return http.build();
//    }

    @Bean
    public JWKSource<SecurityContext> jwkSource() {
        KeyPair keyPair = generateRsaKey();
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
        RSAKey rsaKey = new RSAKey.Builder(publicKey)
                .privateKey(privateKey)
                .keyID(UUID.randomUUID().toString())
                .build();
        JWKSet jwkSet = new JWKSet(rsaKey);
        return new ImmutableJWKSet<>(jwkSet);
    }

    private static KeyPair generateRsaKey() {
        KeyPair keyPair;
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            keyPair = keyPairGenerator.generateKeyPair();
        }
        catch (Exception ex) {
            throw new IllegalStateException(ex);
        }
        return keyPair;
    }

    @Bean
    public JwtDecoder jwtDecoder(JWKSource<SecurityContext> jwkSource) {
        return OAuth2AuthorizationServerConfiguration.jwtDecoder(jwkSource);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().build();
    }

//    private final ClientService clientService;

//    //dahasonra gerekecek -> https://docs.spring.io/spring-authorization-server/reference/core-model-components.html
//    @Bean
//    public OAuth2AuthorizationConsentService authorizationConsentService() {
//        return new InMemoryOAuth2AuthorizationConsentService();
//    }

//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
////        OAuth2AuthorizationServerConfiguration.applyDefaultSecurity(http);
////        http.getConfigurer(OAuth2AuthorizationServerConfigurer.class)
////                .oidc(Customizer.withDefaults());
//
////        OAuth2AuthorizationServerConfigurer authorizationServerConfigurer =
////                new OAuth2AuthorizationServerConfigurer();
////        http.apply(authorizationServerConfigurer);
////        authorizationServerConfigurer
//////                .authorizationConsentService(authorizationConsentService)
////                .registeredClientRepository(clientService);
//
//        http
//                .cors(Customizer.withDefaults())
//                .csrf(AbstractHttpConfigurer::disable)
//                .authorizeHttpRequests(req -> req.requestMatchers(
//                        "/users/**",
//                        "/clients/**"
//                                ).permitAll());
//        return http.build();
//    }






    ////////////////////




//    @Bean
//    JdbcUserDetailsManager jdbcUserDetailsManager(DataSource dataSource) {
//        return new JdbcUserDetailsManager(dataSource);
//    }

//    @Bean
//    RegisteredClientRepository registeredClientRepository(JdbcTemplate jdbcTemplate) {
//        return new JdbcRegisteredClientRepository(jdbcTemplate);
//    }

//    @Bean
//    ApplicationRunner clientsRunner(RegisteredClientRepository registeredClientRepository) {
//        return args -> {
//            var clientId = "client_id";
//            if (registeredClientRepository.findByClientId(clientId) == null) {
//                RegisteredClient registeredClient = RegisteredClient
//                        .withId(UUID.randomUUID().toString())
//                        .clientId(clientId)
//                        // clientId is also client
//                        .clientSecret("{bcrypt}$2a$12$DMHFp5WvLJ8FzBhg0OFxtOMKyMXf7.K0QZwZKjJHn38TuHFMcvxRO")
//                        .authorizationGrantTypes(authorizationGrantTypes -> authorizationGrantTypes
//                                .addAll(Set.of(CLIENT_CREDENTIALS, AUTHORIZATION_CODE, REFRESH_TOKEN)))
//                        .redirectUris(uri -> uri
//                                .add("http://127.0.0.1:8082/login/oauth2/code/reg-client"))
//                        .postLogoutRedirectUris(uri -> uri
//                                .add("http://127.0.0.1:8082/login/oauth2/code/reg-client"))
//                        .scopes(scopes -> scopes
//                                .addAll(Set.of("user.read", "user.write", "openid")))
//                        .clientAuthenticationMethods(cam -> cam
//                                .add(CLIENT_SECRET_BASIC))
//                        .clientSettings(ClientSettings.builder().requireAuthorizationConsent(true).build())
//                        .clientIdIssuedAt(Instant.now())
//                        .build();
//
//                registeredClientRepository.save(registeredClient);
//            }
////            if (!userDetailsManager.userExists("gokhan")) {
////                var userBuilder = User.builder();
////                UserDetails gokhan = userBuilder.username("gokhan").
////                        password("{bcrypt}$2a$12$DAYO9tjSMh77LTLd8N7wyeKw4ibIlPDcF7CxFOJXRKli1o9Mg1j02").roles("USER").build();
////                userDetailsManager.createUser(gokhan);
////            }
////            if (!userDetailsManager.userExists("admin")) {
////                var userBuilder = User.builder();
////                UserDetails admin = userBuilder.username("admin").
////                        password("{bcrypt}$2a$12$Aq6j0qVs8PEDdsJINr1A/uQjE2hfwMK5kwoGyq8VAWuCgZtMGpTCy").roles("USER", "ADMIN").build();
////                userDetailsManager.createUser(admin);
////            }
//        };
//    }
}
