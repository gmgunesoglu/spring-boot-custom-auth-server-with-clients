package com.gokhan.authserver.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDetailDto {

    private String username;
    private String realmName;
    private String clientName;
    private List<String> roles;

}
