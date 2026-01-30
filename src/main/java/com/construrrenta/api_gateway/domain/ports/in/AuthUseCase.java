package com.construrrenta.api_gateway.domain.ports.in;

import com.construrrenta.api_gateway.domain.model.TokenDTO;
import com.construrrenta.api_gateway.domain.model.User;

public interface AuthUseCase {
    TokenDTO login(String email, String password);
    User register(User user);
}
