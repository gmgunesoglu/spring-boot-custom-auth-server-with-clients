package com.gokhan.authserver.dto.resource_server;

import com.gokhan.authserver.entity.MethodType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceServerPoliciesDto {

    private String uri;
    private MethodType method;
    private List<String> roles;
}
