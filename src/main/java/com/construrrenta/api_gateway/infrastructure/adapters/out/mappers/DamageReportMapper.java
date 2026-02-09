package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.construrrenta.api_gateway.domain.model.damage.DamageReport;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.DamageReportEntity;

@Mapper(componentModel = "spring")
public interface DamageReportMapper {
    // De Entidad a Dominio
    default DamageReport toDomain(DamageReportEntity entity) {
        if (entity == null) return null;
        return DamageReport.reconstruct(
            entity.getId(),
            entity.getDescription(),
            entity.getRepairCost(),
            entity.getReportDate(),
            entity.isRepaired(),
            entity.getBookingId()
        );
    }

    // De Dominio a Entidad
    @Mapping(target = "isRepaired", source = "repaired")
    DamageReportEntity toEntity(DamageReport damageReport);
}
