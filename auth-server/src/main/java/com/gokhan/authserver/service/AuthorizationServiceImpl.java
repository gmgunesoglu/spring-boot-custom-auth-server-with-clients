package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.RequestDto;
import com.gokhan.authserver.entity.MethodType;
import com.gokhan.authserver.entity.Policy;
import com.gokhan.authserver.entity.ResourceServer;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.exceptionhandling.GlobalRuntimeException;
import com.gokhan.authserver.repository.PolicyRepository;
import com.gokhan.authserver.repository.ResourceServerRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthorizationServiceImpl implements AuthorizationService {

    private final JwtDecoder jwtDecoder;
    private final PolicyRepository policyRepository;
    private final ResourceServerRepository resourceServerRepository;

    @Override
    public String checkToken(HttpServletRequest request, RequestDto requestDto) {
        Jwt jwt = jwtDecoder.decode(requestDto.getToken());
        Map<String, Object> claims = jwt.getClaims();
        String userName = (String) claims.get("user");
        List<String> authorities = (List<String>) claims.get("authorities");
//        List<String>  aud = (List<String>) claims.get("aud");
//        String clientName = aud.getFirst();

        ResourceServer resourceServer = resourceServerRepository.findByBaseUrl(requestDto.getBaseUrl()).orElseThrow(
                () -> new GlobalRuntimeException("Unregistered Resource Server", HttpStatus.BAD_REQUEST)
        );
        Policy policy = policyRepository.findByResourceServerAndUriAndAndMethodType(resourceServer, requestDto.getUri(), requestDto.getMethod()).orElseThrow(
                () -> new GlobalRuntimeException("Policy not found", HttpStatus.UNAUTHORIZED)
        );
        for (Role role : policy.getRoles()) {
            if (authorities.contains(role.getName())) {
                return "Authorized";
            }
        }
        return "Unauthorized";
    }
}
