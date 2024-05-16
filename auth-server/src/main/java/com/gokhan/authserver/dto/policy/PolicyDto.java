package com.gokhan.authserver.dto.policy;

import com.gokhan.authserver.entity.MethodType;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PolicyDto {

    private String clientName;
    private String resourceServerName;
    private String baseUrl;
    private String uri;
    private MethodType method;
    private List<String> roles;
}
