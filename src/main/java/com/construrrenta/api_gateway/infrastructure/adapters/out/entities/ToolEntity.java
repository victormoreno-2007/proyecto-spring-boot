package com.construrrenta.api_gateway.infrastructure.adapters.out.entities;

import java.math.BigDecimal;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tools")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class ToolEntity {
    
    @Id
    @Column(updatable = false, nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "price_per_day", nullable = false, precision = 10, scale = 2)
    private BigDecimal pricePerDay;

    @Column(name = "image_url", columnDefinition = "TEXT")
    private String imageUrl;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ToolStatus status;

    @Column(name = "provider_id", nullable = false, columnDefinition = "BINARY(16)")
    private UUID providerId; // Relación lógica con User (Proveedor)

    @Column(nullable = false, columnDefinition = "int default 1") 
    private Integer stock;

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID();
        }
    }

}
