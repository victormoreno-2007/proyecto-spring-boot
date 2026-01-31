package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.booking;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.ports.out.BookingRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.BookingEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.ToolEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.UserEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.BookingMapper;
import com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.tool.JpaToolRepository;
import com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.user.JpaUserRepository;

import lombok.RequiredArgsConstructor;


@Component
@RequiredArgsConstructor

public class BookingRepositoryAdapter implements BookingRepositoryPort {

    private final JpaBookingRepository jpaBookingRepository;
    private final JpaUserRepository jpaUserRepository;
    private final JpaToolRepository jpaToolRepository;
    private final BookingMapper bookingMapper;
    
    @Override
    public Booking save(Booking booking) {
        BookingEntity entity = bookingMapper.toEntity(booking);
        // Asignamos las referencias para las llaves foráneas
        UserEntity userRef = jpaUserRepository.getReferenceById(booking.getUserId());
        ToolEntity toolRef = jpaToolRepository.getReferenceById(booking.getToolId());
        entity.setUser(userRef);
        entity.setTool(toolRef);
        
        return bookingMapper.toDomain(jpaBookingRepository.save(entity));
    }

    @Override
    public Optional<Booking> findById(UUID id) {
        return jpaBookingRepository.findById(id).map(bookingMapper::toDomain);
    }

    @Override
    public List<Booking> findAll() {
        return jpaBookingRepository.findAll().stream()
                .map(bookingMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findByUserId(UUID userId) {
        return jpaBookingRepository.findByUserId(userId).stream()
                .map(bookingMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<Booking> findConflictingBookings(UUID toolId, LocalDateTime startDate, LocalDateTime endDate) {
        return jpaBookingRepository.findConflictingBookings(toolId, startDate, endDate).stream()
                .map(bookingMapper::toDomain)
                .collect(Collectors.toList());
    }

}
