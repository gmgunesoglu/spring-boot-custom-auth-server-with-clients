package com.gokhan.authserver.service;

import com.gokhan.authserver.dto.RequestDto;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthorizationService {
    
    String checkToken(HttpServletRequest request, RequestDto requestDto);
}
