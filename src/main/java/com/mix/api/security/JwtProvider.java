package com.mix.api.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Log
public class JwtProvider {

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(Long id, String email) {
        Date date = Date.from(
                LocalDate.now().plusDays(3).atStartOfDay(ZoneId.systemDefault()).toInstant());

        return Jwts.builder()
                .setId(id.toString())
                .setSubject(email)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(date)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            log.severe(e.getMessage());
        }
        return false;
    }

    public String getLoginFromToken(String token) {
        Claims claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
}
