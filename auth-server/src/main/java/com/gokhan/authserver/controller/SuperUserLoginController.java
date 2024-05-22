package com.gokhan.authserver.controller;


import com.gokhan.authserver.dto.user.UserLoginDto;
import com.gokhan.authserver.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.Base64;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class SuperUserLoginController {

    private final UserService userService;

    @GetMapping("super-user-login")
    public String superUserLogin() {
        return "superUserLogin";  // view adını döner
    }

    @GetMapping("login")
    public String login() {
        return "login";  // view adını döner
    }
}
