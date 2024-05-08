package com.gokhan.authserver.dto;

import lombok.Data;

@Data
public class RequestDto {
    private String uri;
    private String method;
    private String token;
}
