package com.jwt.jwt_ex.jwt_configs;
import java.security.Key;
import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtToken {
    static final Logger logger = LoggerFactory.getLogger(JwtToken.class);

    @Value("${app.jwt-secret}")
    String jwt_secret;
    @Value("${app.jwt-expiration-milliseconds}")
    long jwt_expiration;

    public String generateToken(Authentication authentication) {
        String username = authentication.getName();

        Date current_date = new Date();
        Date expire_date = new Date(current_date.getTime() + jwt_expiration);

        String token = Jwts.builder()
                        .setSubject(username)
                        .setIssuedAt(current_date)
                        .setExpiration(expire_date)
                        .signWith(key()) 
                        .compact();

                        return token;
    }

    private Key key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwt_secret));
    }

    public String getUsername(String token)
    {
        Claims claims = Jwts.parserBuilder()
                        .setSigningKey(key())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();
        String username = claims.getSubject();
        return username;                             
    }   

    public Boolean validateToken(String token) 
    {
        try {
            Jwts.parserBuilder().setSigningKey(key()).build().parse(token);
            return true;
         } catch (MalformedJwtException exception) {
           logger.error("invalid jwt token : {} ",exception.getMessage());
        }
        catch(ExpiredJwtException e)
        {
            logger.error("Token expired : {}", e.getMessage());
        }
        catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
