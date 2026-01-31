package com.construrrenta.api_gateway.domain.ports.out;

import java.util.Optional;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.damage.DamageReport;

public interface DamageReportRepositoryPort {
    
    DamageReport save(DamageReport damageReport);

    Optional<DamageReport> findById(UUID id);
    
    Optional<DamageReport> finBookingId(UUID bookingId);
    
}
