package com.gokhan.authserver.controller;

import com.gokhan.authserver.dto.RequestDto;
import com.gokhan.authserver.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authorizations")
@RequiredArgsConstructor
public class AuthorizationController {

    private final JwtDecoder jwtDecoder;
    private final AuthorizationService authorizationService;

    @PostMapping("/check-token")
    public String checkToken(HttpServletRequest request, @RequestBody RequestDto requestDto) {
//        Jwt jwt = jwtDecoder.decode(requestDto.getToken());
//        System.out.println("[jwt] = "+jwt.toString());
//        System.out.println("getIssuer: "+jwt.getIssuer());
//        System.out.println("getAudience: "+jwt.getAudience());
//        System.out.println("getExpiresAt: "+jwt.getExpiresAt());
//        System.out.println("getIssuedAt: "+jwt.getIssuedAt());
//        System.out.println("getNotBefore: "+jwt.getNotBefore());
//        System.out.println("getIssuedAt: "+jwt.getIssuedAt());
//        System.out.println("getClaims: "+jwt.getClaims());
//        Map<String, Object> claims = jwt.getClaims();
//        String userName = (String) claims.get("user");
//        List<String> authorities = (List<String>) claims.get("authorities");
//        List<String> scopes = (List<String>) claims.get("scope");
//        List<String>  aud = (List<String>) claims.get("aud");
//        String clientId = aud.getFirst();
//        return "client_id: " + clientId +
//                "\nuser_name: " + userName +
//                "\nauthorities: " + authorities +
//                "\nscopes: " + scopes +
//                "\nuri: " + requestDto.getUri() +
//                "\nmethod: " + requestDto.getMethod() +
//                "\ntoken: " + requestDto.getToken();
        return authorizationService.checkToken(request, requestDto);
    }
}
