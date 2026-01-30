package com.construrrenta.api_gateway.domain.ports.out;

import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.user.Role;

public interface TokenPort {
    String generateAccessToken(UUID userId, String email, Role role);
    UUID validateAccessToken(String token);
    String extractEmail(String token);
}
