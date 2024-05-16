package com.gokhan.authserver.dto.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private String username;
    private String realmName;
    private String clientName;
    private List<String> roles;

    public UserDto(String username, String realmName, String clientName) {
        this.username = username;
        this.realmName = realmName;
        this.clientName = clientName;
    }
}
