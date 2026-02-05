package com.construrrenta.api_gateway.domain.model.user;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;

import java.util.UUID;

public class User {
    private UUID id;
    private String email;
    private String password;
    private String firstName;
    private String lastName;
    private Role role;

    
    private User() {}

    public User(UUID id, String firstName, String lastName, String email, String password, Role role) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.role = role;
    }
    
    public static User create(String email, String password, String firstName, String lastName, Role role) {
        if (email == null || !email.contains("@")) {
            throw new DomainException("El email es inválido");
        }
        User user = new User();
        user.id = UUID.randomUUID();
        user.email = email;
        user.password = password;
        user.firstName = firstName;
        user.lastName = lastName;
        user.role = role;
        return user;
    }

    
    public static User reconstruct(UUID id, String email, String password, String firstName, String lastName, Role role) {
        User user = new User();
        user.id = id;
        user.email = email;
        user.password = password;
        user.firstName = firstName;
        user.lastName = lastName;
        user.role = role;
        return user;
    }

    
    public UUID getId() { return id; }
    public String getEmail() { return email; }
    public String getPassword() { return password; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public Role getRole() { return role; }


    public void updatePersonalInfo(String firstName, String lastName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new DomainException("El nombre es obligatorio");
        }
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new DomainException("El apellido es obligatorio");
        }
        this.firstName = firstName;
        this.lastName = lastName;
    }

   
    public void changePassword(String newPassword) {
        if (newPassword == null || newPassword.isBlank()) {
            throw new DomainException("La nueva contraseña no puede estar vacía");
        }
        this.password = newPassword;
    }

}

