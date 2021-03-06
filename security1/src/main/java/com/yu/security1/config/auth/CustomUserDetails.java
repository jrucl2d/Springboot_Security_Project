package com.yu.security1.config.auth;

// 로그인을 진행하고 완료되면 security session을 만들어준다. (Security ContextHolder)
// 위 세션에 들어갈 수 있는 오브젝트는 Authentication 객체여야 한다.
// Authentication 안에 User 정보가 있어야 하고 User 오브젝트 타입은 UserDetails 타입 객체

import com.yu.security1.domain.User;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

// Security Session -> Authentication -> UserDetails
@Getter
public class CustomUserDetails implements UserDetails, OAuth2User { // OAuth2User도 여기에 넣어버림

    private User user;
    private Map<String, Object> attributes;

    // 일반 로그인
    public CustomUserDetails(User user){
        this.user = user;
    }
    
    // OAuth 로그인
    public CustomUserDetails(User user, Map<String, Object> attributes){
        this.user = user;
        this.attributes = attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(()->user.getRole());
        return collection;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override // 휴면계정 설정 등에 사용
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    @Override
    public String getName() {
        return null; // 안 중요함
    }
}
