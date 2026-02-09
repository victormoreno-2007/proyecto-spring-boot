package com.construrrenta.api_gateway.infrastructure.adapters.out.security;

import com.construrrenta.api_gateway.domain.model.user.Role;
import com.construrrenta.api_gateway.domain.ports.out.TokenPort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.UUID;

@Service
public class JwtTokenAdapter implements TokenPort {

    private final SecretKey secretKey;
    private final Long accessTokenExpiration;

    // ✅ CAMBIO CRÍTICO: Inyección segura desde application.properties
    // Ya no usamos la clave "quemada" en el código.
    public JwtTokenAdapter(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.expiration}") Long accessTokenExpiration) {
        
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.accessTokenExpiration = accessTokenExpiration;
    }

    @Override
    public String generateAccessToken(UUID userId, String email, Role role, String firstName) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + accessTokenExpiration);

        return Jwts.builder()
                .subject(userId.toString())
                .claim("email", email)
                .claim("role", role.name()) 
                .claim("firstName", firstName)
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
            // ✅ LIMPIEZA: Quitamos el System.out.println para no ensuciar logs en producción
            throw new RuntimeException("Token inválido o expirado", e);
        }
    }

    @Override
    public String extractEmail(String token) {
        return extractClaim(token, "email");
    }

    // ✅ MANTENEMOS ESTO: Es vital para que el filtro de seguridad sepa si eres ADMIN
    @Override
    public String extractRole(String token) {
        return extractClaim(token, "role");
    }

    // Método auxiliar para reutilizar lógica y no repetir código
    private String extractClaim(String token, String claimName) {
        Claims claims = Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
        return claims.get(claimName, String.class);
    }
}