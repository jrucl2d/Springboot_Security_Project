package com.yu.security1.config.auth;

// 로그인을 진행하고 완료되면 security session을 만들어준다. (Security ContextHolder)
// 위 세션에 들어갈 수 있는 오브젝트는 Authentication 객체여야 한다.
// Authentication 안에 User 정보가 있어야 하고 User 오브젝트 타입은 UserDetails 타입 객체

import com.yu.security1.domain.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// Security Session -> Authentication -> UserDetails
public class CustomUserDetails implements UserDetails {

    private User user;

    public CustomUserDetails(User user){
        this.user = user;
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
}
