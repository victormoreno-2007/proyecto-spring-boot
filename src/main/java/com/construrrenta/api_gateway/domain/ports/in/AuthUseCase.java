package com.construrrenta.api_gateway.domain.ports.in;


import com.construrrenta.api_gateway.domain.model.user.User;

public interface AuthUseCase {
    User register(User user);
}
