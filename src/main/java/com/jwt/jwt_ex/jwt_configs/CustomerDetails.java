package com.jwt.jwt_ex.jwt_configs;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.jwt_ex.Entity.User;
import com.jwt.jwt_ex.Repos.Urepo;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CustomerDetails implements UserDetailsService {
    
    private Urepo urepo;

    @Override
    public UserDetails loadUserByUsername(String usernameOrEmail) throws UsernameNotFoundException 
    {
        User user = urepo.findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
        .orElseThrow(() -> new UsernameNotFoundException("User dont exist with this username or Email")); 

        Set<GrantedAuthority> authorities = user
        .getRoles()
        .stream()
        .map((role) -> new SimpleGrantedAuthority(user.getName()))
        .collect(Collectors.toSet());

        return new org.springframework.security.core.userdetails.User(
        usernameOrEmail,
        user.getPassword(),
        authorities);
    }
}
