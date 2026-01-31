package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.BookingEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "tool.id", target = "toolId")
    Booking toDomain(BookingEntity entity);

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "toolId", target = "tool.id")
    BookingEntity toEntity(Booking booking);
}