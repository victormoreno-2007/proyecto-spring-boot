package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers;

import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.BookingEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.UserEntity;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BookingMapper {

    // Inyectamos el ToolMapper para poder convertir la herramienta completa (con foto y nombre)
    private final ToolMapper toolMapper; 

    public Booking toDomain(BookingEntity entity) {
        if (entity == null) return null;

        return Booking.reconstruct(
            entity.getId(),
            entity.getUser().getId(),
            entity.getTool() != null ? toolMapper.toDomain(entity.getTool()) : null,
            entity.getPaymentId(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getTotalPrice(),
            entity.getStatus(),
            entity.getReturnStatus()
        );
    }

    public BookingEntity toEntity(Booking domain) {
        if (domain == null) return null;

        return BookingEntity.builder()
                .id(domain.getId())
                .user(UserEntity.builder().id(domain.getUserId()).build())
                .tool(ToolEntity.builder().id(domain.getToolId()).build())
                .startDate(domain.getStartDate())
                .endDate(domain.getEndDate())
                .totalPrice(domain.getTotalPrice())
                .status(domain.getStatus())
                .paymentId(domain.getPaymentId())
                .returnStatus(domain.getReturnStatus())
                .build();
    }
}