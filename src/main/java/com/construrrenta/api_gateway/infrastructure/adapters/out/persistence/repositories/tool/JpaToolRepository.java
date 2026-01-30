package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.tool;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;

public interface JpaToolRepository extends JpaRepository<ToolEntity, UUID>{
    
}
