package com.jwt.jwt_ex.Servuces;

import com.jwt.jwt_ex.Entity.UserDao;

public interface LoginService {
    
    String login(UserDao userDao); 
}
