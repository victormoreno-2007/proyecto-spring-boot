package com.construrrenta.api_gateway.application.services;

import java.util.List;

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
        // ✅ CORRECCIÓN:
        // Ya NO intentamos usar tool.setStatus(AVAILABLE).
        // Como usaste Tool.create() en el Controller, la herramienta
        // ya viene con el estado correcto. Solo la guardamos.
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
}
