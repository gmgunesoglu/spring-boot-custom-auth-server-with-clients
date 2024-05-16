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
public class RealmDto {
    // super user, roller, client lar
    private String realName;
    private String superUserName;
    private String clientName;
    private List<String> rolesName;
}
