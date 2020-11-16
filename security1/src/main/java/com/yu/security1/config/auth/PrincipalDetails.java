package com.yu.security1.config.auth;

import com.yu.security1.model.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

// security가 /login 요청을 낚아채서 로그인 진행시키고 완료되면 security session(security contextHolder)를 만들어줌
// 오브젝트 타입이 Authentication인 객체를 저장하는데 이 안에 User 정보가 들어간다. User 오브젝트 타입을 USerDetails타입 객체로.
// Security Session => Authentication => UserDetails에서 UserDetails(PrincipalDetails)를 만든다.
public class PrincipalDetails implements UserDetails {

    private User user; // composition

    public PrincipalDetails(User user){
        this.user = user;
    }

    // 해당 User의 권한을 리턴하는 곳
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> collection = new ArrayList<>();
        collection.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                return user.getRole();
            }
        });
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

    @Override
    public boolean isEnabled() {
        // 휴면 계정으로 변하게 되면 이것을 false로.
        return true;
    }
}
