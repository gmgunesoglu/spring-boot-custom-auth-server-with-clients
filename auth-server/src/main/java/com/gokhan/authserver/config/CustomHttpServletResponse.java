package com.gokhan.authserver.config;

import jakarta.servlet.http.HttpServletResponse;

public interface CustomHttpServletResponse extends HttpServletResponse {

    int SC_REFRESH_TOKEN = 480;

}
