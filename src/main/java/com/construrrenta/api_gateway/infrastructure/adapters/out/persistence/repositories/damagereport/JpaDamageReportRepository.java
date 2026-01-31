package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.damagereport;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.DamageReportEntity;

public interface JpaDamageReportRepository extends JpaRepository<DamageReportEntity, UUID> {
    // Spring Data JPA crea automáticamente el query por nombre de método
    Optional<DamageReportEntity> findByBookingId(UUID bookingId);
}