package com.yu.jwtserver.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.yu.jwtserver.auth.CustomUserDetails;
import com.yu.jwtserver.domain.User;
import com.yu.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Security의 filter 중 BasicAuthenticationFilter는 권한이나 인증이 필요할 경우에만 작동
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private UserRepository userRepository;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, UserRepository userRepository) {
        super(authenticationManager);
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String jwtHeader = request.getHeader("Authorization");

        // 헤더 존재 여부 확인
        if(jwtHeader == null || !jwtHeader.startsWith("Bearer")){
            chain.doFilter(request, response);
            return;
        }

        // JWT 토큰 검증
        String token = jwtHeader.replace("Bearer ", "");
        String username = JWT.require(Algorithm.HMAC512("mysecret")).build().verify(token).getClaim("username").asString();

        if(username != null){
            User user = userRepository.findByUsername(username);

            CustomUserDetails customUserDetails = new CustomUserDetails(user);
            // JWT 토큰 서명을 통해 서명 정상이면 authentication 객체 생성. 임시 authentication 객체이므로 credential null
            Authentication authentication = new UsernamePasswordAuthenticationToken(customUserDetails, null, customUserDetails.getAuthorities());

            // 강제로 session에 접근하여 authentication 객체를 저장
            SecurityContextHolder.getContext().setAuthentication(authentication);

        }
        chain.doFilter(request, response);
 
    }
}
