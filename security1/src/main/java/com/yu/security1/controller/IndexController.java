package com.yu.security1.controller;

import com.yu.security1.config.auth.CustomUserDetails;
import com.yu.security1.domain.User;
import com.yu.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequiredArgsConstructor
public class  IndexController {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @GetMapping("/test/login")
    public @ResponseBody String testLogin(Authentication authentication, @AuthenticationPrincipal CustomUserDetails userDetails){
        // DI를 통해 Authentiation 안에 UserDetails 정보가 있고 그 안에 User 정보가 있음
        // 혹은 어노테이션을 통해 유저 정보 가져올 수 있다.
        CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal(); // Object이므로 다운캐스팅
        System.out.println("오센티 = " + customUserDetails.getUser());
        System.out.println("유저테일 = " + userDetails.getUser());
        return "세션 정보 확인";
    }

    @GetMapping("/test/oauth/login")
    public @ResponseBody String testOauthLogin(Authentication authentication, @AuthenticationPrincipal OAuth2User userDetails){
        // Oauth에서는 CustomUserDetails로 캐스팅이 안 됨 -> CustomUserDetails가 OAuth2User로 implement하게 하자.
        OAuth2User customUserDetails = (OAuth2User) authentication.getPrincipal();
        System.out.println("오어스 오센티 = " + customUserDetails.getAttributes());
        System.out.println("오어스 유저테일 = " + userDetails.getAttributes());
        return "오어스 세션 정보 확인";
    }

    @GetMapping({"", "/"})
    public String index() {
        // view resolver의 templates(prefix), .mustache(suffix) 생략 가능
        return "index";
    }

    // 일반 로그인 해도, OAuth 로그인 해도 유저 정보 가져올 수 있다.
    @GetMapping("/user")
    public @ResponseBody String user(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        System.out.println("유저 정보 : " + customUserDetails.getUser());
        return "user";
    }

    @GetMapping("/admin")
    public @ResponseBody String admin() {
        return "admin";
    }

    @GetMapping("/manager")
    public @ResponseBody String manager() {
        return "manager";
    }

    // 스프링 시큐리티가 이 주소를 원래 낚아채지만 SecurityConfig 파일 생성하면 작동 안 함
    @GetMapping("/loginForm")
    public String login() {
        return "loginForm";
    }
    @GetMapping("/joinForm")
    public String joinForm() {
        return "joinForm";
    }
    
    @PostMapping("/join")
    public String join(User user) {
        System.out.println(user);
        user.setRole("ROLE_USER");
        String rawPassword = user.getPassword();
        String encPassword = passwordEncoder.encode(rawPassword);
        user.setPassword(encPassword);
        userRepository.save(user);
        return "redirect:/loginForm";
    }

    @Secured("ROLE_ADMIN")
    @GetMapping("/info")
    public @ResponseBody String info(){
        return "개인정보";
    }

    @PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
    @GetMapping("/data")
    public @ResponseBody String data(){
        return "개인정보2";
    }
}