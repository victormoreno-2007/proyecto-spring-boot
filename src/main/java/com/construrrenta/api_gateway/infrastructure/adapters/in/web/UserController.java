package com.construrrenta.api_gateway.infrastructure.adapters.in.web;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize; // <--- IMPORTANTE
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.construrrenta.api_gateway.domain.model.user.Role;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.in.ManageUserUseCase;
import com.construrrenta.api_gateway.infrastructure.adapters.in.web.dto.RegisterRequest;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final ManageUserUseCase manageUserUseCase;

    // Solo Admin puede ver la lista completa
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(manageUserUseCase.getAllUsers());
    }

    // Solo Admin puede ver detalles por ID (o podrías ajustarlo para el propio usuario)
    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<User> getUserById(@PathVariable UUID id) {
        return ResponseEntity.ok(manageUserUseCase.getUserById(id));
    }
    
    // Solo Admin puede eliminar usuarios
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<User> deleteUser(@PathVariable UUID id) {
        manageUserUseCase.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // Solo Admin puede crear usuarios directos (Panel Admin)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')") 
    public ResponseEntity<User> createUser(@RequestBody CreateUserRequest request) {
        User newUser = new User(
            null, 
            request.getFirstName(),
            request.getLastName(),
            request.getEmail(),
            request.getPassword(),
            request.getRole()
        );
        return ResponseEntity.ok(manageUserUseCase.createUser(newUser));
    }

    // DTO interno
    @lombok.Data
    public static class CreateUserRequest {
        private String firstName;
        private String lastName;
        private String email;
        private String password;
        private Role role; 
    }

    // Este lo dejamos abierto a cualquier autenticado para editar SU perfil
    // (Idealmente validarías que el ID sea el del usuario logueado, pero por ahora Auth es suficiente)
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable UUID id, @RequestBody RegisterRequest request) {
        User userUpdates = User.reconstruct(
            id, 
            null, 
            request.getPassword(), 
            request.getFirstName(), 
            request.getLastName(), 
            null 
        );

        User updatedUser = manageUserUseCase.updateUser(id, userUpdates);
        return ResponseEntity.ok(updatedUser);
    }
}