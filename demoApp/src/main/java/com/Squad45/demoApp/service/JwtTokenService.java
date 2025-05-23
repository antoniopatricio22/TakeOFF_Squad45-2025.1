package com.Squad45.demoApp.service;

import io.github.cdimascio.dotenv.Dotenv;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

//import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;

@Service
public class JwtTokenService {
    
    private final SecretKey key;
    private final long expirationMillis;

    //@Autowired
    public JwtTokenService(Dotenv dotenv) {
        byte[] keyBytes = Decoders.BASE64.decode(dotenv.get("JWT_SECRET"));
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.expirationMillis = Long.parseLong(dotenv.get("JWT_EXPIRATION"));
    }

    public String generateToken(Authentication authentication) {
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();
        return Jwts.builder() 
                   .subject(userDetails.getUsername()) // username é o email neste caso
                   .claim("role", role)
                   .issuedAt(new Date()) 
                   .expiration(new Date(System.currentTimeMillis() + expirationMillis))  
                   .signWith(key) 
                   .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getUsernameFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    public String getRoleFromToken(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            return claims.get("role", String.class);
        } catch (Exception e) {
            return null;
        }
    }
}