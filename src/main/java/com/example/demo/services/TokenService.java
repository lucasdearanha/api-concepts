package com.example.demo.services;

import com.example.demo.exceptions.TokenJwtExpiredOrIncorrectException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;

@Service
public class TokenService {
    public String generateToken(Authentication auth) {
        String email = auth.getName();
        var claims = new HashMap<String, Object>();

        Instant now = Instant.now();
        Instant expireDate = now.plus(1, ChronoUnit.HOURS);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .claim("authorities", auth.getAuthorities())
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(expireDate))
                .signWith(SignatureAlgorithm.HS512, "jwt_secret")
                .compact();
    }

    public String getEmailFromJwt(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey("jwt_secret")
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }

    public boolean isValidToken(String token) {

        try {
            Jwts.parser().setSigningKey("jwt_secret").parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            throw new TokenJwtExpiredOrIncorrectException("Json Web Token expirou ou esta incorreto");
        }
    }

}