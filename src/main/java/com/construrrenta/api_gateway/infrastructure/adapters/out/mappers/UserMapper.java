package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers;

import org.mapstruct.Mapper;

import com.construrrenta.api_gateway.domain.model.User;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.UserEntity;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toEntity(User user);

    default User toDomain(UserEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return User.reconstruct(
            entity.getId(),        
            entity.getEmail(),
            entity.getPassword(),
            entity.getFirstName(),
            entity.getLastName(),
            entity.getRole()       
        );
    }

}
