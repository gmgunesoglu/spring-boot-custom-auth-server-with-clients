package com.gokhan.resourceserver.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

    @GetMapping("/hello")
    public String hello(@AuthenticationPrincipal Jwt jwt) {
        System.out.println("[jwt] = "+jwt.toString());
        System.out.println("getIssuer: "+jwt.getIssuer());
        System.out.println("getAudience: "+jwt.getAudience());
        System.out.println("getExpiresAt: "+jwt.getExpiresAt());
        System.out.println("getIssuedAt: "+jwt.getIssuedAt());
        System.out.println("getNotBefore: "+jwt.getNotBefore());
        System.out.println("getIssuedAt: "+jwt.getIssuedAt());
        System.out.println("getClaims: "+jwt.getClaims());

        return "Hello from resource server";
    }
}