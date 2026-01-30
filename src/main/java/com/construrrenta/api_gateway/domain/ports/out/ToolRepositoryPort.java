package com.construrrenta.api_gateway.domain.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.tool.Tool;

public interface ToolRepositoryPort {
    Tool save(Tool tool);
    Optional<Tool> findById(UUID id);
    List<Tool> findAll();
}
