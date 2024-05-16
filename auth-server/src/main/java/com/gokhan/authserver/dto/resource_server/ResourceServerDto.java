package com.gokhan.authserver.dto.resource_server;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceServerDto {

    private String clientName;
    private String name;
    private String baseUrl;
}
