package com.construrrenta.api_gateway.domain.ports.in;

import java.util.List;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.tool.Tool;

public interface ToolUseCase {
    Tool createTool(Tool tool); // Opción 1: Crear
    List<Tool> getAllTools();   // Opción 2: Ver catálogo
    List<Tool> getAvailableTools(); // Opción 3: Ver solo disponibles
    List<Tool> getToolsByProvider(UUID providerId);
    Tool updateTool(UUID id, Tool toolDetails);
    void deleteTool(UUID id);
    Tool getToolById(UUID id); // Útil para detalles
}
