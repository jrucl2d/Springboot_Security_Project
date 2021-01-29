package com.yu.jwtserver.repository;

import com.yu.jwtserver.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findByUsername(String username);
}
