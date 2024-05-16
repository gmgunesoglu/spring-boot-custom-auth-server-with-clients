package com.gokhan.authserver.dto.role;

import lombok.Data;
import lombok.Getter;

@Getter
public class RoleRegisterDto {

    private String superUserName;
    private String roleName;
}
