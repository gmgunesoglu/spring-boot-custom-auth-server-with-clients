package com.gokhan.authserver.dto.policy;

import com.gokhan.authserver.entity.MethodType;
import lombok.Getter;

import java.util.List;

@Getter
public class PolicyRegisterDto {

    private String clientName;
    private String resourceServerName;
    private String uri;
    private MethodType method;
        private List<String> roles;
}
