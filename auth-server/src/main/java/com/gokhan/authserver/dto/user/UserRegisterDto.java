package com.gokhan.authserver.dto.user;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
public class UserRegisterDto {
    private String username;
    private String password;
    private List<String> roles;
    private String clientName;
}
