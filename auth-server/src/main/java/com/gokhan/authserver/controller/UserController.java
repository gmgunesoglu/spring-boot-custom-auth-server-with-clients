package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.UserRegisterDto;
import com.gokhan.authserver.entity.User;
import com.gokhan.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtDecoder jwtDecoder;

    @PostMapping
    public User add(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.add(userRegisterDto);
    }

    @GetMapping
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public User get(@PathVariable Long id) {
        return userService.get(id);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public User delete(@PathVariable Long id) {
        return userService.delete(id);
    }

//    @GetMapping
//    public String loadUsers(){
//        var userBuilder = User.builder();
//        UserDetails user = userDetailsManager.loadUserByUsername("gokhan");
//        System.out.println("neler oluyor viiir");
//
//        if (user == null) {
//            user = userBuilder.username("gokhan").
//                    password("{bcrypt}$2a$12$DAYO9tjSMh77LTLd8N7wyeKw4ibIlPDcF7CxFOJXRKli1o9Mg1j02").roles("ADMIN").build();
//            userDetailsManager.createUser(user);
//
//        }
//
//        user = userDetailsManager.loadUserByUsername("admin");
//        if (user == null) {
//            user = userBuilder.username("admin").
//                    password("{bcrypt}$2a$12$Aq6j0qVs8PEDdsJINr1A/uQjE2hfwMK5kwoGyq8VAWuCgZtMGpTCy").roles("USER", "ADMIN").build();
//            userDetailsManager.createUser(user);
//        }
//        return "users loaded";
//    }


}
