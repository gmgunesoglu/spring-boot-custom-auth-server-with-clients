package com.gokhan.authserver.dto.realm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RealmDetailDto {
    // super user, roller, client lar, resource-server lar...
    private String realmName;
    private String superUserName;
    private String clientName;
    private List<String> rolesName;
    private List<String> resourceServersName;
}
