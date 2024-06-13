package com.gokhan.authserver.dto.realm;

import lombok.Getter;
import org.springframework.validation.annotation.Validated;

@Validated
@Getter
public class RealmRegisterDto {

    private String superUserName;
    private String name;
}
