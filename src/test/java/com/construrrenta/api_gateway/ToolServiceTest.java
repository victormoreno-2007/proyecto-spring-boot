package com.construrrenta.api_gateway;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.construrrenta.api_gateway.application.services.ToolService;
import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.domain.ports.out.ToolRepositoryPort;

@ExtendWith(MockitoExtension.class)
class ToolServiceTest {

    @Mock
    private ToolRepositoryPort toolRepositoryPort;

    @InjectMocks
    private ToolService toolService;

    private UUID toolId;
    private Tool mockTool;

    @BeforeEach
    void setUp() {
        toolId = UUID.randomUUID();
        mockTool = Tool.reconstruct(toolId, "Martillo", "Fuerte", new BigDecimal("10.00"), "img.png", ToolStatus.AVAILABLE, UUID.randomUUID(), 5);
    }

    @Test
    void createTool_Exitoso_GuardaEnBD() {
        when(toolRepositoryPort.save(any(Tool.class))).thenReturn(mockTool);

        Tool result = toolService.createTool(mockTool);

        assertNotNull(result);
        assertEquals("Martillo", result.getName());
        verify(toolRepositoryPort).save(mockTool);
    }

    @Test
    void getToolById_NoExiste_LanzaExcepcion() {
        when(toolRepositoryPort.findById(toolId)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            toolService.getToolById(toolId);
        });

        assertEquals("Herramienta no encontrada", exception.getMessage());
    }
}