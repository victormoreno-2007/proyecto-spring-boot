package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.tool;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;

public interface JpaToolRepository extends JpaRepository<ToolEntity, UUID> {
    // Spring crea el SQL automático: SELECT * FROM tools WHERE status = ?
    List<ToolEntity> findByStatus(ToolStatus status);
    List<ToolEntity> findByProviderId(UUID providerId);
    List<ToolEntity> findByNameContainingIgnoreCase(String name);
}
