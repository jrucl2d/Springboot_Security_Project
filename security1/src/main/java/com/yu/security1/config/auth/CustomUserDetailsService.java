package com.yu.security1.config.auth;

import com.yu.security1.domain.User;
import com.yu.security1.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// login 요청이 오면 자동으로 UserDetailsService 타입으로 IoC 되어있는 loadByUsername 함수가 실행
// 함수 종료시 @AuthenticationPrincipal 어노테이션이 활성화 됨
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    // security session ) Authentication 객체 ) UserDetails
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user != null){
            return new CustomUserDetails(user);
        }
        return null;
    }
}
