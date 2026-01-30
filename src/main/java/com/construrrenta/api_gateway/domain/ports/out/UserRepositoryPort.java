package com.construrrenta.api_gateway.domain.ports.out;

import java.util.Optional;

import com.construrrenta.api_gateway.domain.model.User;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    
    
    User save(User user);
    

    boolean existsByEmail(String email);
}
