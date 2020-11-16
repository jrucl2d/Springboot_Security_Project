package com.yu.security1.config.auth;

import com.yu.security1.model.User;
import com.yu.security1.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

// Security 설정에서 loginProcessingUrl("/login")으로 login 요청이 오면 자동으로
// UserDetailsService 타입으로 IoC되어 있는 loadUserByUsername 함수가 실행된다.
@Service
public class PrincipalDetailsService implements UserDetailsService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) {
            return null;
        }
        return new PrincipalDetails(user);
    }

}