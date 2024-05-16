package com.gokhan.authserver.dto.resource_server;

import lombok.Getter;

@Getter
public class ResourceServerRegisterDto {

    private String clientName;
    private String name;
    private String baseUrl;
}
