package com.gokhan.authserver.controller;

import com.gokhan.authserver.config.CustomHttpServletResponse;
import com.gokhan.authserver.dto.RequestDto;
import com.gokhan.authserver.service.AuthorizationService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/authorizations")
@RequiredArgsConstructor
public class AuthorizationController {

    private final AuthorizationService authorizationService;

    @PostMapping("/check")
    public int checkToken(@RequestBody RequestDto requestDto) {
        return authorizationService.checkToken(requestDto);
    }
}
