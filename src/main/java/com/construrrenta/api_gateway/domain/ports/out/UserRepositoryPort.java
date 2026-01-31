package com.construrrenta.api_gateway.domain.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.user.User;

public interface UserRepositoryPort {
    Optional<User> findByEmail(String email);
    
    Optional<User> findById(UUID id);

    User save(User user);
    
    boolean existsByEmail(String email);

    List<User> findAll();

    void deleteById(UUID id);
}
