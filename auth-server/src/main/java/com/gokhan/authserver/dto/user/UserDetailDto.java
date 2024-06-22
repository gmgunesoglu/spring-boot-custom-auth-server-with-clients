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
    private boolean blocked;
    private List<String> roles;

    public UserDetailDto(String username, String realmName, String clientName, boolean blocked) {
        this.username = username;
        this.realmName = realmName;
        this.clientName = clientName;
        this.blocked = blocked;
    }
}
