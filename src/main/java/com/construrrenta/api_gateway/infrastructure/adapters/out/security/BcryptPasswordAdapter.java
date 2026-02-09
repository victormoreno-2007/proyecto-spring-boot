package com.construrrenta.api_gateway.infrastructure.adapters.out.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.construrrenta.api_gateway.domain.ports.out.PasswordPort;

@Service
public class BcryptPasswordAdapter implements PasswordPort{
 private final PasswordEncoder passwordEncoder;

    public BcryptPasswordAdapter(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public String hash(String plainPassword) {
        return passwordEncoder.encode(plainPassword);
    }

    @Override
    public boolean matches(String plainPassword, String hashedPassword) {
        return passwordEncoder.matches(plainPassword, hashedPassword);
    }   
}
