package com.construrrenta.api_gateway.domain.ports.out;

public interface PasswordPort {
    String hash(String plainPassword);
    boolean matches(String plainPassword, String hashedPassword);
}
