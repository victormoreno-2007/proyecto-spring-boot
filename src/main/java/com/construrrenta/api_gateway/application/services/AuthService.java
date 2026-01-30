package com.construrrenta.api_gateway.application.services;

import org.springframework.stereotype.Service;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.TokenDTO;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.in.AuthUseCase;
import com.construrrenta.api_gateway.domain.ports.out.PasswordPort;
import com.construrrenta.api_gateway.domain.ports.out.TokenPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

@Service
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordPort passwordPort;
    private final TokenPort tokenPort;

    
    public AuthService(UserRepositoryPort userRepository, PasswordPort passwordPort, TokenPort tokenPort) {
        this.userRepository = userRepository;
        this.passwordPort = passwordPort;
        this.tokenPort = tokenPort;
    }

    @Override
    public TokenDTO login(String email, String password) {
        // 1. Buscar usuario
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new DomainException("Credenciales inválidas")); 

        // 2. Verificar contraseña
        if (!passwordPort.matches(password, user.getPassword())) {
            throw new DomainException("Credenciales inválidas");
        }

        // 3. Generar Token
        String token = tokenPort.generateAccessToken(user.getId(), user.getEmail(), user.getRole());
        
        return new TokenDTO(token);
    }

    @Override
    public User register(User user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DomainException("El email ya está registrado: " + user.getEmail());
        }

        String hashedPassword = passwordPort.hash(user.getPassword());

        User userToSave = User.reconstruct(
            user.getId(), 
            user.getEmail(),
            hashedPassword, 
            user.getFirstName(),
            user.getLastName(),
            user.getRole()
        );

        return userRepository.save(userToSave);
    }
}
