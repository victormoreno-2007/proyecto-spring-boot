package com.construrrenta.api_gateway.infrastructure.adapters.in.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.construrrenta.api_gateway.domain.model.TokenDTO;
import com.construrrenta.api_gateway.domain.model.user.Role;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.in.AuthUseCase;
import com.construrrenta.api_gateway.infrastructure.adapters.in.web.dto.RegisterRequest;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {
 
    private final AuthUseCase authUseCase;

    public AuthController(AuthUseCase authUseCase) {
        this.authUseCase = authUseCase;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDTO> login(@RequestBody LoginRequest request) { 
        return ResponseEntity.ok(authUseCase.login(request.getEmail(), request.getPassword()));
    }
    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody RegisterRequest request) {
        User newUser = User.create(
            request.getEmail(),
            request.getPassword(),
            request.getFirstName(),
            request.getLastName(),
            Role.CUSTOMER 
        );
        
        User createdUser = authUseCase.register(newUser);
        
        return ResponseEntity.ok(createdUser);
    }
}
