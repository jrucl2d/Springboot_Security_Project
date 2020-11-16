package com.yu.security1.repository;

import com.yu.security1.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

// CRUD 함수를 들고 있다.
// @Repository는 없어도 된다. JpaRepository를 상속했기 때문에.
public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByUsername(String username);
}
