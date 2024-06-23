package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.user.*;
import com.gokhan.authserver.entity.User;
import com.gokhan.authserver.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/super-user")
    public UserDetailDto addSuperUser(@RequestBody SuperUserRegisterDto superUserRegisterDto) {
        return userService.addSuperUser(superUserRegisterDto);
    }

    @PostMapping
    public UserDetailDto add(@RequestBody UserRegisterDto userRegisterDto) {
        return userService.add(userRegisterDto);
    }

    @GetMapping
    public List<UserDto> getAll() {
        return userService.getAll();
    }

    @GetMapping("/list/{username}")
    public List<UserDto> getAllByUsername(@PathVariable String username) {
        return userService.getAllByUsername(username);
    }


    @GetMapping("/{username}")
    public UserDetailDto getByUsername(@PathVariable String username) {
        return userService.getByUsername(username);
    }

    @PutMapping("/{id}")
    public User update(@PathVariable Long id, @RequestBody User user) {
        return null;
    }

    @PostMapping("/{id}/set-roles")
    public UserDetailDto setRoles(@PathVariable Long id, @RequestBody UserSetRoleDto userSetRoleDto) {
        return userService.setRoles(id, userSetRoleDto);
    }

    @PostMapping("/block/{username}")
    public UserDetailDto block(@PathVariable String username){
        return userService.block(username);
    }

    @PostMapping("/unblock/{username}")
    public UserDetailDto unblock(@PathVariable String username){
        return userService.unblock(username);
    }

    // TODO -> silme işlemleri ile ilgili iş lojiği henüz düşünülmedi
    @DeleteMapping("/{id}")
    public UserDetailDto delete(@PathVariable Long id) {
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
