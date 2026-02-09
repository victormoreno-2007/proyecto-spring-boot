package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.userAdmin;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.user.Role;
import com.construrrenta.api_gateway.domain.model.user.User;
import com.construrrenta.api_gateway.domain.ports.out.PasswordPort;
import com.construrrenta.api_gateway.domain.ports.out.UserRepositoryPort;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepositoryPort userRepository;
    private final PasswordPort passwordPort;

    public DataSeeder(UserRepositoryPort userRepository, PasswordPort passwordPort) {
        this.userRepository = userRepository;
        this.passwordPort = passwordPort;
    }

    @Override
    public void run(String... args) throws Exception {
        String adminEmail = "admin@construrrenta.com";
        
        if (!userRepository.existsByEmail(adminEmail)) {
            
            System.out.println("Insertando usuario Administrador inicial...");
            
            String password = passwordPort.hash("admin123"); 
            
           
            User admin = User.create(
                adminEmail,
                password,
                "Super",
                "Admin",
                Role.ADMIN 
            );
            
           
            userRepository.save(admin);
            
            System.out.println("ADMIN CREADO...");
        }
    }
}
