package com.construrrenta.api_gateway.infrastructure.adapters.in.web;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.construrrenta.api_gateway.domain.model.tool.Tool;
import com.construrrenta.api_gateway.domain.ports.in.ToolUseCase;

import org.springframework.web.bind.annotation.RequestBody;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/tools") // La URL base será: localhost:8080/api/v1/tools
@RequiredArgsConstructor
public class ToolController {

    private final ToolUseCase toolUseCase;

    @PostMapping
    public ResponseEntity<Tool> createTool(@RequestBody ToolRequest request) {
        // Validar que venga el providerId (Temporal hasta que integren Seguridad)
        if (request.getProviderId() == null) {
            // Podrías lanzar una excepción o retornar bad request
            return ResponseEntity.badRequest().build();
        }

        Tool newTool = Tool.create(
                request.getName(),
                request.getDescription(),
                request.getPricePerDay(),
                request.getImageUrl(),
                request.getProviderId() // <--- Usamos el ID real que viene del Postman
        );

        return ResponseEntity.ok(toolUseCase.createTool(newTool));
    }

    @GetMapping
    public ResponseEntity<List<Tool>> getAllTools() {
        return ResponseEntity.ok(toolUseCase.getAllTools());
    }

    // 👇 CLASE MANUAL (Sin @Data para evitar errores de Lombok)
    public static class ToolRequest {
        private String name;
        private String description;
        private BigDecimal pricePerDay;
        private String imageUrl;
        private UUID providerId; // <--- Agrega esto

        // Constructor Vacío (Obligatorio para Jackson)
        public ToolRequest() {
        }

        // Getters y Setters Manuales
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public BigDecimal getPricePerDay() {
            return pricePerDay;
        }

        public void setPricePerDay(BigDecimal pricePerDay) {
            this.pricePerDay = pricePerDay;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public UUID getProviderId() {
            return providerId;
        }

        public void setProviderId(UUID providerId) {
            this.providerId = providerId;
        }
    }
}
