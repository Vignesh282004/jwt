package com.jwt.jwt_ex.Servuces;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.jwt.jwt_ex.Entity.UserDao;
import com.jwt.jwt_ex.Repos.Urepo;
import com.jwt.jwt_ex.jwt_configs.JwtToken;

@Service
public class LoginServeuce implements LoginService {
    private Urepo urepo;
    private PasswordEncoder passwordEncoder;
    private JwtToken jwtToken;
    private AuthenticationManager authenticationManager;

    public LoginServeuce(Urepo urepo, PasswordEncoder passwordEncoder, JwtToken jwtToken,
            AuthenticationManager authenticationManager) {
        this.urepo = urepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtToken = jwtToken;
        this.authenticationManager = authenticationManager;
    }


    @Override
    public String login(UserDao userDao) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDao.getUsernameOrEmail(), userDao.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtToken.generateToken(authentication);
        return token;
    }

}
