package com.gokhan.resourceserver.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/member")
    public String member(){
        return "member";
    }

    @GetMapping("/customer")
    public String customer(){
        return "customer";
    }

    @GetMapping("/staff")
    public String staff(){
        return "staff";
    }
}
