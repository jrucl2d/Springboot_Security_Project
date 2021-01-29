package com.yu.jwtserver.auth;

import com.yu.jwtserver.domain.User;
import com.yu.jwtserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// 원래 http://localhost:8000/login 요청이 올 때 동작함
// 그러나 FormLogin을 disable 해놔서 동작 안 함 -> 필터가 필요
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User theUser = userRepository.findByUsername(s);
        return new CustomUserDetails(theUser);
    }
}
