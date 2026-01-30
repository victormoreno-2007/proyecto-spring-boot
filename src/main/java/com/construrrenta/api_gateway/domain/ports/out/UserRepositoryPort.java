package com.construrrenta.api_gateway.domain.ports.out;

import java.util.Optional;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.user.User;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    
    Optional<User> findById(UUID id);

    User save(User user);
    

    boolean existsByEmail(String email);
}
