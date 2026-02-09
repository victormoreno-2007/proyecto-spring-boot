package com.construrrenta.api_gateway.application.services;

import org.springframework.stereotype.Service;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.in.AuthUseCase;
import com.construrrenta.api_gateway.domain.ports.out.PasswordPort;
import com.construrrenta.api_gateway.domain.ports.out.TokenPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

@Service
public class AuthService implements AuthUseCase {

    private final UserRepositoryPort userRepository;
    private final PasswordPort passwordPort;
   

    
    public AuthService(UserRepositoryPort userRepository, PasswordPort passwordPort, TokenPort tokenPort) {
        this.userRepository = userRepository;
        this.passwordPort = passwordPort;
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
