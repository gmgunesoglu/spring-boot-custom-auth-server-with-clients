package com.gokhan.resourceserverandclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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