package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers;

import org.mapstruct.Mapper;

import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;

@Mapper(componentModel = "spring")
public interface ToolMapper {
    ToolEntity toEntity(Tool tool);

    default Tool toDomain(ToolEntity entity){
        if (entity == null) {
            return null;
        }

        return Tool.reconstruct(entity.getId(),
        entity.getName(), 
        entity.getDescription(), 
        entity.getPricePerDay(), 
        entity.getImageUrl(), 
        entity.getStatus(), 
        entity.getProviderId(),
        entity.getStock());

    }
}
