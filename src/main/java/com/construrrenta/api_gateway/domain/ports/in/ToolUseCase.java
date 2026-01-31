package com.construrrenta.api_gateway.domain.ports.in;

import java.util.List;

import com.construrrenta.api_gateway.domain.model.tool.Tool;

public interface ToolUseCase {
    Tool createTool(Tool tool); // Opción 1: Crear
    List<Tool> getAllTools();   // Opción 2: Ver catálogo
    List<Tool> getAvailableTools(); // Opción 3: Ver solo disponibles
}
