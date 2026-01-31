package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.mappers;
import com.construrrenta.api_gateway.domain.model.damage.DamageReport;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.DamageReportEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DamageReportMapper {
    
    DamageReport toDomain(DamageReportEntity entity);
    
    DamageReportEntity toEntity(DamageReport domain);
}
