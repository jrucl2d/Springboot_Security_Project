package com.yu.security1.config.oauth;

import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

// 구글로부터 받은 userRequest 데이터에 대한 후처리되는 함수
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {
    @Override // userRequest 안에 Access Token과 사용자 정보가 들어있다. 코드 받는 부분 생략
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        System.out.println("유저 리퀘스트 " + userRequest);
        // super.loadUser(userRequest) 안의 정보를 토대로 회원가입 강제 진행
        return super.loadUser(userRequest);
    }
}
