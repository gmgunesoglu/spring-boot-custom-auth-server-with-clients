package com.gokhan.authserver.service;

import com.gokhan.authserver.config.CustomHttpServletResponse;
import com.gokhan.authserver.dto.RequestDto;
import com.gokhan.authserver.entity.MethodType;
import com.gokhan.authserver.entity.Policy;
import com.gokhan.authserver.entity.ResourceServer;
import com.gokhan.authserver.entity.Role;
import com.gokhan.authserver.exceptionhandling.GlobalRuntimeException;
import com.gokhan.authserver.repository.PolicyRepository;
import com.gokhan.authserver.repository.ResourceServerRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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
    public int checkToken(RequestDto requestDto) {
        Jwt jwt = jwtDecoder.decode(requestDto.getToken());
        Map<String, Object> claims = jwt.getClaims();
        List<String> authorities = (List<String>) claims.get("authorities");
        ResourceServer resourceServer = resourceServerRepository.findByBaseUrl(requestDto.getBaseUrl()).get();
        if (resourceServer == null){
            return HttpStatus.BAD_REQUEST.value();
        }
        Policy policy = policyRepository.findByResourceServerAndUriAndAndMethodType(resourceServer, requestDto.getUri(), requestDto.getMethod()).orElse(null);
        if (policy == null){
            return HttpStatus.NOT_FOUND.value();
        }
        for (Role role : policy.getRoles()) {
            if (authorities.contains(role.getName())) {
                return HttpStatus.OK.value();
            }
        }
        return HttpStatus.FORBIDDEN.value();
    }
}
