package com.construrrenta.api_gateway.domain.ports.out;

import java.time.LocalDateTime;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.booking.Booking;

public interface ManageBookingUseaCase {
    Booking createBooking(UUID userId, UUID toolid, LocalDateTime startDate, LocalDateTime endDate);
    Booking getBooking(UUID id);
}
