package com.gokhan.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.security.Principal;

@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    private OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/{role}")
    public String message(Principal principal, @PathVariable String role) {
        var restTemplate = new RestTemplate();

        HttpHeaders httpHeaders = new HttpHeaders();
        String accessToken = authorizedClientService
                .loadAuthorizedClient("reg-client", principal.getName())
                .getAccessToken().getTokenValue();
        httpHeaders.set("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(httpHeaders);
        ResponseEntity<String> response =
                restTemplate.exchange("http://localhost:8080/test/" + role, HttpMethod.GET, entity, String.class);

        return "Success  :: " + response.getBody();
    }
}