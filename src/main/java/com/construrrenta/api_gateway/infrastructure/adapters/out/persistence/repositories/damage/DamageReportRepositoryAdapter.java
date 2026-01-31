package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.damage;

import com.construrrenta.api_gateway.domain.model.damage.DamageReport;
import com.construrrenta.api_gateway.domain.ports.out.DamageReportRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.mappers.DamageReportMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository 
@RequiredArgsConstructor
public class DamageReportRepositoryAdapter implements DamageReportRepositoryPort {

    private final JpaDamageReportRepository jpaRepository;
    private final DamageReportMapper mapper;

    @Override
    public DamageReport save(DamageReport damageReport) {
        var entity = mapper.toEntity(damageReport);
        var saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<DamageReport> findById(UUID id) {
        return jpaRepository.findById(id)
                .map(mapper::toDomain);
    }

    @Override
    public Optional<DamageReport> finBookingId(UUID bookingId) {
        return jpaRepository.findByBookingId(bookingId)
                .map(mapper::toDomain);
    }
}
