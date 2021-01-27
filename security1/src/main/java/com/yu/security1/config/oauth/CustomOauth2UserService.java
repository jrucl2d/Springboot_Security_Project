package com.yu.security1.config.oauth;

import com.yu.security1.config.auth.CustomUserDetails;
import com.yu.security1.config.oauth.provider.FacebookUserInfo;
import com.yu.security1.config.oauth.provider.GoogleUserInfo;
import com.yu.security1.config.oauth.provider.NaverUserInfo;
import com.yu.security1.config.oauth.provider.OAuth2UserInfo;
import com.yu.security1.domain.User;
import com.yu.security1.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
// 함수 종료시 @AuthenticationPrincipal 어노테이션이 활성화됨
public class CustomOauth2UserService extends DefaultOAuth2UserService {

    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final String DUMMY_PASSWORD = "GGerlGGerlMan";

    public CustomOauth2UserService(BCryptPasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    // userRequest 안에 Access Token과 사용자 정보가 들어있다. 코드 받는 부분 생략
    // 구글 로그인 버튼 클릭 -> 구글 로그인 창 -> 로그인 완료 -> code 리턴(OAuth-Client라이브러리) -> Access Token 요청
    // userRequest 정보 -> loadUser 함수 호출 -> 구글로부터 회원프로필 받아즘
    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // super.loadUser(userRequest) 안의 정보를 토대로 회원가입 강제 진행
        OAuth2User oauth2User = super.loadUser(userRequest);
        String provider = userRequest.getClientRegistration().getRegistrationId();

        OAuth2UserInfo oAuth2UserInfo = null;
        if(provider.equals("google")){
            oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
        } else if(provider.equals("facebook")){
            oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
        }else if(provider.equals("naver")){
            oAuth2UserInfo = new NaverUserInfo((Map)oauth2User.getAttributes().get("response"));
        }
        String providerId = oAuth2UserInfo.getProviderId();
        String email = oAuth2UserInfo.getEmail();
        String username = provider + "_" + providerId; // google_1235985792385723
        String password = passwordEncoder.encode(DUMMY_PASSWORD);
        String role = "ROLE_USER";

        // 이미 회원가입 되어있는지 확인
        User user = userRepository.findByUsername(username);
        if(user == null){
            user = User.builder()
                    .username(username)
                    .password(password)
                    .email(email)
                    .role(role)
                    .provider(provider)
                    .providerId(providerId)
                    .build();
            userRepository.save(user);
        }
        return new CustomUserDetails(user, oauth2User.getAttributes());
    }
}