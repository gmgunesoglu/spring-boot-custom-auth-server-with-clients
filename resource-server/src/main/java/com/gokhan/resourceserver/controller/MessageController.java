package com.gokhan.resourceserver.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.http.HttpRequest;

@RestController
public class MessageController {

    @GetMapping("/hello")
    public String hello() {


        return "Hello from resource server";
    }



//    @PreAuthorize("hasAnyAuthority('SCOPE_user.read')")
    @GetMapping("/test")
    public String test() {
        return "test ok";
    }
}