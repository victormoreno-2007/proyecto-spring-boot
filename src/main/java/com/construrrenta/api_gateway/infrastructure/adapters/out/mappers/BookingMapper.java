package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.BookingEntity;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "toolId", source = "tool.id")
    default Booking toDomain(BookingEntity entity) {
        if (entity == null) return null;
        return Booking.reconstruct(
            entity.getId(),
            entity.getUser().getId(),
            entity.getTool().getId(),
            entity.getPaymentId(),
            entity.getStartDate(),
            entity.getEndDate(),
            entity.getTotalPrice(),
            entity.getStatus()
        );
    }

    @Mapping(target = "user", ignore = true) 
    @Mapping(target = "tool", ignore = true)
    BookingEntity toEntity(Booking booking);
    
}
