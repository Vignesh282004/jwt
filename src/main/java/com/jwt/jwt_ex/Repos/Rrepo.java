package com.jwt.jwt_ex.Repos;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jwt.jwt_ex.Entity.Role;

public interface Rrepo  extends JpaRepository<Role,Long>{
    
    Optional<Role> findByName(String name);

}
