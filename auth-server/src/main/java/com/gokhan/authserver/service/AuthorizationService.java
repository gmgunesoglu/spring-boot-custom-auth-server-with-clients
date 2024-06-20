package com.gokhan.authserver.service;

import com.gokhan.authserver.config.CustomHttpServletResponse;
import com.gokhan.authserver.dto.RequestDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;

public interface AuthorizationService {

    int checkToken(RequestDto requestDto);
}
