package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.user;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.UserEntity;

public interface JpaUserRepository extends JpaRepository<UserEntity, UUID> { 
    
    Optional<UserEntity> findByEmail(String email);
    
    boolean existsByEmail(String email);
}
