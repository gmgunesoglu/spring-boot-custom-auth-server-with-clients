package com.gokhan.authserver.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserRegisterDto {

    private String username;
    private String password;
    private List<String> roles;
    private String clientName;
}
