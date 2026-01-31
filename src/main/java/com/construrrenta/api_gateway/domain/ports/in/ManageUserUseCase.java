package com.construrrenta.api_gateway.domain.ports.in;

import java.util.List;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.user.User;

public interface ManageUserUseCase {
    List<User> getAllUsers();
    User getUserById(UUID id);
    void deleteUser(UUID id);
}
