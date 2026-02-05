package com.construrrenta.api_gateway.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private final PasswordEncoder passwordEncoder;

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
    public User createUser(User user) {
        
        if (userRepositoryPort.findByEmail(user.getEmail()).isPresent()) {
            throw new DomainException("El email ya está registrado");
        }

        // Encriptar contraseña
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        
        User userToSave = new User(
            null,                // ID (se genera al guardar o aquí)
            user.getFirstName(),
            user.getLastName(),
            user.getEmail(),
            encodedPassword,     // Password cifrado
            user.getRole()
        );

        return userRepositoryPort.save(userToSave);
    }

    @Override
    public void deleteUser(UUID id) {
        if (userRepositoryPort.findById(id).isEmpty()) {
            throw new DomainException("no se puede eliminar porque el usuario no existe");
        }
        userRepositoryPort.deleteById(id);
    }

    @Override
    public User updateUser(UUID id, User userDetails) {
        User existingUser = userRepositoryPort.findById(id)
                .orElseThrow(() -> new DomainException("Usuario no encontrado"));

        // Actualizamos datos básicos usando el método de dominio
        existingUser.updatePersonalInfo(
            userDetails.getFirstName(), 
            userDetails.getLastName()
        );

        // Si viene contraseña, se usea el método específico
        if (userDetails.getPassword() != null && !userDetails.getPassword().isBlank()) {
            String encodedPass = passwordEncoder.encode(userDetails.getPassword());
            existingUser.changePassword(encodedPass);
        }

        return userRepositoryPort.save(existingUser);
    }

}
