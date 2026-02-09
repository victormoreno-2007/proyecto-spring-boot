package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.user;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.UserEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.UserMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UserRepositoryAdapter implements UserRepositoryPort {

    private final JpaUserRepository jpaUserRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<User> findByEmail(String email) {
        return jpaUserRepository.findByEmail(email)
                .map(userMapper::toDomain);
    }

    @Override
    public Optional<User> findById(UUID id) {
        return jpaUserRepository.findById(id) // Este método ya lo trae JpaRepository por defecto
                .map(userMapper::toDomain);
    }
    
    @Override
    public User save(User user) {
        UserEntity entity = userMapper.toEntity(user);
        UserEntity savedEntity = jpaUserRepository.save(entity);
        return userMapper.toDomain(savedEntity);
    }

    @Override
    public boolean existsByEmail(String email) {
        return jpaUserRepository.existsByEmail(email);
    }

    @Override
    public List<User> findAll() {
        return jpaUserRepository.findAll().stream()
        .map(userMapper::toDomain)
        .toList();
    }

    @Override
    public void deleteById(UUID id) {
        if (jpaUserRepository.existsById(id)) {
            jpaUserRepository.deleteById(id);
        }
    }
}
