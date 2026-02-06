package com.construrrenta.api_gateway.domain.ports.out;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import com.construrrenta.api_gateway.domain.model.booking.Booking;

public interface BookingRepositoryPort {
    Booking save(Booking booking);
    Optional<Booking> findById(UUID id);
    List<Booking> findAll();
    List<Booking> findByUserId(UUID userId);
    List<Booking> findConflictingBookings(UUID toolId, LocalDateTime startDate, LocalDateTime endDate);
    List<Booking> findByProviderId(UUID providerId);
}