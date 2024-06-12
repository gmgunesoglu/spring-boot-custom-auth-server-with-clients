//package com.gokhan.authserver.authentication;
//
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import org.springframework.stereotype.Component;
//
//import java.io.IOException;
//
//@Component
//public class CustomUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
//
//
//    public CustomUsernamePasswordAuthenticationFilter(CustomAuthenticationManager authenticationManager) {
//        super.setAuthenticationManager(authenticationManager);
//    }
//
//    @Override
//    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
//        String username = request.getParameter("username");
//        String password = request.getParameter("password");
//
//        // Ek kontrol ve doğrulama işlemleri burada yapılabilir
//        // Örneğin, kullanıcı adının ve şifrenin belirli bir formatta olup olmadığını kontrol edebilirsiniz
//
//        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, password);
//
//        // Ek bilgiler eklemek isterseniz buraya ekleyebilirsiniz
//        // authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//
//        return this.getAuthenticationManager().authenticate(authRequest);
//    }
//
//    @Override
//    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException, ServletException {
//        // Başarılı kimlik doğrulama sonrası işlemler burada yapılabilir
//        // Örneğin, ek loglama işlemleri veya kullanıcıya özel yanıtlar dönebilirsiniz
//
//        super.successfulAuthentication(request, response, chain, authResult);
//    }
//
//    @Override
//    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
//        // Başarısız kimlik doğrulama sonrası işlemler burada yapılabilir
//        // Örneğin, hata mesajları veya ek loglama işlemleri
//
//        super.unsuccessfulAuthentication(request, response, failed);
//    }
//}
