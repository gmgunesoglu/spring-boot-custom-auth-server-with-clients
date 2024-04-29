package com.gokhan.resourceserver.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authz -> authz.anyRequest()
                        .authenticated())
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
        return httpSecurity.build();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        http
//                .authorizeHttpRequests(authorize -> authorize
//                        .requestMatchers("/messages/**").access(hasScope("message:read"))
//                        .anyRequest().authenticated()
//                )
//                .oauth2ResourceServer(oauth2 -> oauth2
//                        .jwt(jwt -> jwt
//                                .jwtAuthenticationConverter(myConverter())
//                        )
//                );
//        return http.build();
//    }


//    @Bean
//    SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
//        // @formatter:off
//        http
//                .authorizeExchange((authorize) -> authorize
//                        .pathMatchers(HttpMethod.GET, "/message/**").hasAuthority("SCOPE_message:read")
//                        .pathMatchers(HttpMethod.POST, "/message/**").hasAuthority("SCOPE_message:write")
//                        .anyExchange().authenticated()
//                )
//                .oauth2ResourceServer((resourceServer) -> resourceServer
//                        .jwt(withDefaults())
//                );
//        // @formatter:on
//        return http.build();
//    }

//    @Bean
//    public JwtDecoder jwtDecoder() {
//        return JwtDecoders.fromIssuerLocation(issuerUri);
//    }
}