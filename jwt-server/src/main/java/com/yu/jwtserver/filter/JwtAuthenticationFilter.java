package com.yu.jwtserver.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yu.jwtserver.auth.CustomUserDetails;
import com.yu.jwtserver.domain.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

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

        try{
//            BufferedReader bf = request.getReader();
//            String input = null;
//            while((input = bf.readLine()) != null){
//                System.out.println(input);
//            }

            ObjectMapper om = new ObjectMapper(); // json으로 parsing
            User user = om.readValue(request.getInputStream(), User.class);

            UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());

            Authentication authentication = authenticationManager.authenticate(token);

            // return을 통해 authentication 객체가 session 영역에 저장됨
            // 권한 관리를 security가 대신 해주기 때문에 편해지기 위해서 리턴한다.
            // JWT 토큰 사용시 세션 만들 필요 없는데 권한 처리 때문에 session에 넣어준다.
            return authentication;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    // attemptAuthentication 실행 후 인증 정상적으로 되면 실행됨
    // JWT 토큰을 만들어서 request 요청한 사용자에게 JWT 토큰을 response해주면 됨
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        CustomUserDetails userDetails = (CustomUserDetails) authResult.getPrincipal();

        String jwtToken = JWT.create()
                .withSubject("mytoken")
                .withExpiresAt(new Date(System.currentTimeMillis() + (60 * 1000 * 10)))
                .withClaim("id", userDetails.getUser().getId())
                .withClaim("username", userDetails.getUsername())
                .sign(Algorithm.HMAC512("mysecret"));

        response.addHeader("Authorization", "Bearer " + jwtToken);
    }
}
