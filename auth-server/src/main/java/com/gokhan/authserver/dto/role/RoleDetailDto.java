package com.gokhan.authserver.dto.role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RoleDetailDto {

    private String name;
    private String realmName;
    private String superUserName;
}
