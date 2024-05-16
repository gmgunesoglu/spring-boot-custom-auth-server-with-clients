package com.gokhan.authserver.dto.user;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class SuperUserRegisterDto {
    private String username;
    private String password;
}
