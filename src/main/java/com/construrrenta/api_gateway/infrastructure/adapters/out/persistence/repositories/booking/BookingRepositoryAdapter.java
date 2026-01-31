package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.booking;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.ports.out.BookingRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.BookingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository // <--- ¡ESTO ES LO QUE SPRING ESTÁ BUSCANDO!
@RequiredArgsConstructor
public class BookingRepositoryAdapter implements BookingRepositoryPort {

    private final JpaBookingRepository jpaBookingRepository;
    private final BookingMapper bookingMapper;

    @Override
    public Booking save(Booking booking) {
        var entity = bookingMapper.toEntity(booking);
        var savedEntity = jpaBookingRepository.save(entity);
        return bookingMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Booking> findbyId(UUID id) {
        return jpaBookingRepository.findById(id)
                .map(bookingMapper::toDomain);
    }

    @Override
    public List<Booking> finAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'finAll'");
    }

    @Override
    public List<Booking> findConflictingBookings(UUID toolId, LocalDateTime startDate, LocalDateTime endDate) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findConflictingBookings'");
    }
}