package com.yu.jwtserver.filter;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// spring security에서 /login 요청시 username, password를 post로 전송하면
// UsernamePasswordAuthenticationFilter가 동작함. 그러나 formLogin 비활성화되어 있어서 필터 등록 필요
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;

    // /login 요청시 로그인 시도를 위해서 실행되는 함수
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        
        // 1. username, password 받아서
        
        // 2. 로그인 시도 -> authenticationManager로 로그인 시도. CustomUerDetailsService의 loadBy...가 실행됨

        // 3. CustomUserDetails를 세션에 담고 -> 권한 관리를 위해서. 권한 관리 필요 없다면 안 담아도 됨

        // 4. JWT토큰 만들어서 응답
        
        return super.attemptAuthentication(request, response);
    }
}
