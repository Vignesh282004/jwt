package com.jwt.jwt_ex.Servuces;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.jwt_ex.Entity.JwtResponse;
import com.jwt.jwt_ex.Entity.UserDao;

@RestController
@RequestMapping("/jwt")
public class JwtController {
    @Autowired
    private LoginServeuce loginServeuce;

    @GetMapping("/access")
    public String access() {
        return "working";
    }
    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody UserDao userDao) 
    {
        String token = loginServeuce.login(userDao);

        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setAccessToken(token);

        return ResponseEntity.ok(jwtResponse);
    }

}
