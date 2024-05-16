package com.gokhan.authserver.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDetailDto {
    // super_user, users, realm, resource_servers
    private String superUserName;
    private String realmName;
    private String name;
    private List<String> usersName;
    private List<String> resourceServersName;
}
