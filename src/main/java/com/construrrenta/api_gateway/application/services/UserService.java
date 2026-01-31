package com.construrrenta.api_gateway.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.in.ManageUserUseCase;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService implements ManageUserUseCase{
    
    private final UserRepositoryPort userRepositoryPort;

    @Override
    public List<User> getAllUsers() {
        return userRepositoryPort.findAll();
    }

    @Override
    public User getUserById(UUID id) {
        return userRepositoryPort.findById(id)
        .orElseThrow(() -> new DomainException("Usuario no encontrado con el id " + id));
    }

    @Override
    public void deleteUser(UUID id) {
        if (userRepositoryPort.findById(id).isEmpty()) {
            throw new DomainException("no se puede eliminar porque el usuario no existe");
        }
        userRepositoryPort.deleteById(id);
    }


}
