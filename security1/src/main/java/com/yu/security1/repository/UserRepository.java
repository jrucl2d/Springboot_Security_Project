package com.yu.security1.repository;

import com.yu.security1.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

// @Repository 없어도 IoC 가능. 상속하므로
public interface UserRepository extends JpaRepository<User, Integer> {

    public User findByUsername(String username);
}
