package com.gokhan.resourceserver.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestDto {
    private String uri;
    private String baseUrl;
    private String method;
    private String token;
}
