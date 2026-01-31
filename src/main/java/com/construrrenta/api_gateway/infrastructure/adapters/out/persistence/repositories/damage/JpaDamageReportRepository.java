package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.damage;

import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.DamageReportEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface JpaDamageReportRepository extends JpaRepository<DamageReportEntity, UUID> {
    Optional<DamageReportEntity> findByBookingId(UUID bookingId);
}
