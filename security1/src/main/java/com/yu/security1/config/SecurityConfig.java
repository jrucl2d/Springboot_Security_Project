package com.yu.security1.config;

import com.yu.security1.config.oauth.PrincipalOauth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

// Oauth2.0 진행과정
// 1. 코드받기(인증), 2. 엑세스토큰(권한), 3. 사용자 프로필 정보를 가져옴, 4-1. 정보 토대로 회원가입 자동 진행, 4-2. 정보 토대로 추가 정보 등록

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터가 스프링 필터 체인에 등록됨
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true) // secured 어노테이션 활성화, preAuthorize, postAuthorize 어노테이션 활성화
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final PrincipalOauth2UserService principalOauth2UserService;

    @Bean // 해당 메소드의 리턴되는 객체를 IoC로 등록해줌
    public BCryptPasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll()
                .and()
                .formLogin()
                .loginPage("/loginForm")
                .loginProcessingUrl("/login") // loginForm에서 post로 전해진 내용으로 로그인 진행
                .defaultSuccessUrl("/") // loginForm에서 로그인 하면 /으로. 특정 페이지에서 로그인하면 특정 페이지로 넘겨줌
                .and()
                .oauth2Login()
                .loginPage("/loginForm") // http://localhost:8000/login/oauth2/code/google가 리다이렉트 주소여야 함
                .userInfoEndpoint()
                .userService(principalOauth2UserService); // 구글 로그인 후의 후처리(Google Oauth Client를 사용하면 코드를 받지 않고 바로 엑세스토큰+사용자 정보를 받음)
    }
}
