package com.example.services.impl;

import com.example.entities.RegisteredUser;
import com.example.services.JWTService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class JWTServiceImpl implements JWTService {

    private static final long EXPIRATION_TIME = 35 * 1000 * 60; // 5 minutes

    private final String jwtSecret;

    public JWTServiceImpl(@Value("${jwt.secret}") String jwtSecret) {
        this.jwtSecret = jwtSecret;
    }

    public String generateToken(RegisteredUser registeredUser) {
        return Jwts.builder()
                .setSubject(registeredUser.getEmail())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    public boolean validateJwtToken(String token, RegisteredUser registeredUser) {
        String username = getUsernameFromToken(token);
        Claims claims = Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
        boolean isTokenExpired = claims.getExpiration().before(new Date());
        return !isTokenExpired && username.equals(registeredUser.getEmail());
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(jwtSecret)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

}
