package com.jwt.jwt_ex.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.jwt_ex.Entity.User;

public interface Urepo extends JpaRepository<User,Long> {
    
    User findByUsername(String username);

    Boolean existsByEmail(String email);

    Optional<User> findByUsernameOrEmail(String username,String email);

    boolean existsByUsername(String username);

}
