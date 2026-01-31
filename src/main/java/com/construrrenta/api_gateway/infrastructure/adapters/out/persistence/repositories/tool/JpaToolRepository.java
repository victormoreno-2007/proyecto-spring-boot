package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.tool;

<<<<<<< Updated upstream
=======
import java.util.List;
>>>>>>> Stashed changes
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

<<<<<<< Updated upstream
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;

public interface JpaToolRepository extends JpaRepository<ToolEntity, UUID>{
    
=======
import com.construrrenta.api_gateway.domain.model.tool.ToolStatus;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;

public interface JpaToolRepository extends JpaRepository<ToolEntity, UUID> {
    // Spring crea el SQL automático: SELECT * FROM tools WHERE status = ?
    List<ToolEntity> findByStatus(ToolStatus status);
>>>>>>> Stashed changes
}
