package com.gokhan.authserver.dto;

import com.gokhan.authserver.entity.MethodType;
import lombok.Data;

@Data
public class RequestDto {
    private String uri;
    private String baseUrl;
    private MethodType method;
    private String token;
}
