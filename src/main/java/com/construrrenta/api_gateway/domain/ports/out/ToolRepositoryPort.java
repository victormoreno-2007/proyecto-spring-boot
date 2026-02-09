package com.construrrenta.api_gateway.domain.ports.out;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.tool.Tool;

import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;

public interface ToolRepositoryPort {
    Tool save(Tool tool);                     // Guardar una herramienta
    Optional<Tool> findById(UUID id);         // Buscar por ID (Puede no existir, por eso Optional)
    List<Tool> findAll();                     // Traer todas
    List<Tool> findByStatus(ToolStatus status); // Filtrar (ej: Solo las DISPONIBLES)
    List<Tool> findByProviderId(UUID providerId); // Para ver MIS herramientas
    void deleteById(UUID id);
    List<Tool> searchToolsByName(String name);
}
