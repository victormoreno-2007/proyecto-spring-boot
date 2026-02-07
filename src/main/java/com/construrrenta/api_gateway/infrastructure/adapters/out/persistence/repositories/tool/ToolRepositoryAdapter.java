package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.tool;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.ToolMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ToolRepositoryAdapter implements ToolRepositoryPort {

    private final JpaToolRepository jpaToolRepository;
    private final ToolMapper toolMapper;

    @Override
    public Tool save(Tool tool) {
        // 1. Traducir a lenguaje de BD
        ToolEntity entity = toolMapper.toEntity(tool);
        // 2. Guardar en MySQL
        ToolEntity savedEntity = jpaToolRepository.save(entity);
        // 3. Traducir de vuelta a lenguaje de Dominio
        return toolMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Tool> findById(UUID id) {
        return jpaToolRepository.findById(id).map(toolMapper::toDomain);
    }

    @Override
    public List<Tool> findAll() {
        return jpaToolRepository.findAll().stream()
                .map(toolMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tool> findByStatus(ToolStatus status) {
        return jpaToolRepository.findByStatus(status).stream()
                .map(toolMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Tool> findByProviderId(UUID providerId) {
        return jpaToolRepository.findByProviderId(providerId)
                .stream()
                .map(toolMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(UUID id) {
        if (jpaToolRepository.existsById(id)) {
            jpaToolRepository.deleteById(id);
        }
    }

    @Override
    public List<Tool> searchToolsByName(String name) {
        return jpaToolRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(toolMapper::toDomain)
                .collect(Collectors.toList());
    }
    
}
