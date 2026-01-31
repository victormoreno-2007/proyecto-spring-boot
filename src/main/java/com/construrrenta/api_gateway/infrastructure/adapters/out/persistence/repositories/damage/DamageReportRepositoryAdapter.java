package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.damage;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.damage.DamageReport;
import com.construrrenta.api_gateway.domain.ports.out.DamageReportRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.DamageReportEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.DamageReportMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DamageReportRepositoryAdapter implements DamageReportRepositoryPort {

    private final JpaDamageReportRepository jpaDamageReportRepository;
    private final DamageReportMapper damageReportMapper;

    @Override
    public DamageReport save(DamageReport damageReport) {
        DamageReportEntity entity = damageReportMapper.toEntity(damageReport);
        DamageReportEntity saved = jpaDamageReportRepository.save(entity);
        return damageReportMapper.toDomain(saved);
    }

    @Override
    public Optional<DamageReport> findById(UUID id) {
        return jpaDamageReportRepository.findById(id)
                .map(damageReportMapper::toDomain);
    }

    // Corrección del nombre del método (antes tenías un typo 'finBookingId')
    @Override
    public Optional<DamageReport> finBookingId(UUID bookingId) {
        return jpaDamageReportRepository.findByBookingId(bookingId)
                .map(damageReportMapper::toDomain);
    }
}