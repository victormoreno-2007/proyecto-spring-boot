package com.construrrenta.api_gateway.application.services;

import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.domain.ports.in.ToolUseCase;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ToolService implements ToolUseCase {
    private final ToolRepositoryPort toolRepositoryPort;

    @Override
    public Tool createTool(Tool tool) {

        return toolRepositoryPort.save(tool);
    }

    @Override
    public List<Tool> getAllTools() {
        return toolRepositoryPort.findAll();
    }

    @Override
    public List<Tool> getAvailableTools() {
        return toolRepositoryPort.findByStatus(ToolStatus.AVAILABLE);
    }

    @Override
    public List<Tool> getToolsByProvider(UUID providerId) {
        return toolRepositoryPort.findByProviderId(providerId);
    }

    @Override
    public void deleteTool(UUID id) {
        toolRepositoryPort.deleteById(id);
    }

    @Override
    public Tool getToolById(UUID id) {
        return toolRepositoryPort.findById(id)
                .orElseThrow(() -> new RuntimeException("Herramienta no encontrada"));
    }

    @Override
    public Tool updateTool(UUID id, Tool toolDetails) {
        // 1. Buscamos la herramienta existente
        Tool existingTool = getToolById(id);

        // 2. Actualizamos los datos (Creando una nueva instancia porque Tool es
        // inmutable o usando setters si los tienes)
        // Opción A: Si usas reconstruct (Recomendada por tu arquitectura)
        Tool updatedTool = Tool.reconstruct(
                existingTool.getId(),
                toolDetails.getName(),
                toolDetails.getDescription(),
                toolDetails.getPricePerDay(),
                toolDetails.getImageUrl(),
                toolDetails.getStatus(),
                existingTool.getProviderId(), // El dueño NO cambia
                toolDetails.getStock()
        );

        // 3. Guardamos
        return toolRepositoryPort.save(updatedTool);
    }

}
