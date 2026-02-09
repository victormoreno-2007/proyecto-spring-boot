package com.construrrenta.api_gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.construrrenta.api_gateway.application.services.UserService;
import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.user.Role;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepositoryPort userRepositoryPort;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private User newUser;

    @BeforeEach
    void setUp() {
        newUser = new User(null, "Juan", "Perez", "juan@test.com", "123456", Role.CUSTOMER);
    }

    @Test
    void createUser_EmailYaExiste_LanzaExcepcion() {
        // Simulamos que la BD dice "Este email ya está registrado"
        when(userRepositoryPort.findByEmail("juan@test.com")).thenReturn(Optional.of(mock(User.class)));

        // Intentamos crear y esperamos un DomainException
        DomainException exception = assertThrows(DomainException.class, () -> {
            userService.createUser(newUser);
        });

        assertEquals("El email ya está registrado", exception.getMessage());
        // Verificamos que NUNCA se intentó guardar en BD
        verify(userRepositoryPort, never()).save(any(User.class));
    }

    @Test
    void createUser_Exitoso_EncriptaPasswordYGuarda() {
        // Simulamos que el email está libre
        when(userRepositoryPort.findByEmail(anyString())).thenReturn(Optional.empty());
        // Simulamos el encriptador
        when(passwordEncoder.encode("123456")).thenReturn("hashed_password");
        // Simulamos el guardado
        when(userRepositoryPort.save(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User result = userService.createUser(newUser);

        assertNotNull(result);
        assertEquals("hashed_password", result.getPassword()); // Verificamos que se encriptó
        verify(userRepositoryPort).save(any(User.class)); // Verificamos que se guardó
    }
}