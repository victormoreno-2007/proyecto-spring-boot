package com.construrrenta.api_gateway.infrastructure.adapters.out.security;

import com.construrrenta.api_gateway.domain.model.user.Role;
import com.construrrenta.api_gateway.domain.ports.out.TokenPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;


import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

public class JwtTokenAdapter implements TokenPort {

    private final SecretKey secretKey;
    private final Long accessTokenExpiration;

    public JwtTokenAdapter(
            @Value("${jwt.secret:404E635266556A586E3272357538782F413F4428472B4B6250645367566B5970}") String secret,
            @Value("${jwt.access-token-expiration:900000}") Long accessTokenExpiration) {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
    }

    @Override
    public String generateAccessToken(UUID userId, String email, Role role) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(userId.toString()) 
                .claim("email", email)
                .claim("role", role.name())
                .issuedAt(now)     
                .expiration(expiryDate) 
                .signWith(secretKey)
                .compact();
    }

    @Override
    public UUID validateAccessToken(String token) {
        try {
           
            Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token) 
                    .getPayload();           

            return UUID.fromString(claims.getSubject());
        } catch (Exception e) {
            throw new RuntimeException("Token inválido", e);
        }
    }
    
    @Override
    public String extractEmail(String token) {
         Claims claims = Jwts.parser()
                    .verifyWith(secretKey)
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
         return claims.get("email", String.class);
    }
}
