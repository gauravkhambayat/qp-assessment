package com.example.qp.assessment.grocery.jwt;
import com.example.qp.assessment.grocery.service.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.UUID;

@Component
public class JwtUtil {

    //private final String SECRET_KEY = "rYXNzL29HZFBLV3BmV0dndmxMVHd0WkdmYnBrUVU1TzQ=";
    private final SecretKey secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512); // Generate a secure key

    public String generateToken(UserDetailsImpl userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .claim("id", userDetails.getId().toString()) // Convert UUID to String
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 10 hours
                .signWith(secretKey)
                .compact();
    }

    public String getUsernameFromToken(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().getSubject();
    }

    public UUID getUserIdFromToken(String token) {
        String idString = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody().get("id", String.class);
        return UUID.fromString(idString); // Convert String back to UUID
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (IllegalArgumentException | JwtException e) {
            e.printStackTrace();
        }
        return false;
    }
}
