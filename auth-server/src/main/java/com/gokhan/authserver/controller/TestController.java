package com.gokhan.authserver.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.server.authorization.OAuth2AuthorizationService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    private final OAuth2AuthorizationService sessionManager;

    @GetMapping
    public String test() {
        return "test ok";
    }

//    @GetMapping("/sessions")
//    public List<String> getSessions(){
//
//        return sessionManager.
//    }
}
