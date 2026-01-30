package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.tool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.ToolMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ToolRepositoryAdapter implements ToolRepositoryPort{
    
    private final JpaToolRepository jpaToolRepository;
    private final ToolMapper toolMapper;
    @Override
    public Tool save(Tool tool) {
       ToolEntity entity = toolMapper.toEntity(tool);
       ToolEntity savEntity = jpaToolRepository.save(entity);
       return toolMapper.toDomain(savEntity);
    }
    @Override
    public Optional<Tool> findById(UUID id) {
        return jpaToolRepository.findById(id)
        .map(toolMapper::toDomain);
    }
    @Override
    public List<Tool> findAll() {
       return jpaToolRepository.findAll().stream()
       .map(toolMapper::toDomain)
       .collect(Collectors.toList());
    }


}
