package com.construrrenta.api_gateway.domain.ports.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.model.damage.DamageReport;

public interface ManageBookingUseCase {
    Booking createBooking(UUID userId, UUID toolid, LocalDateTime startDate, LocalDateTime endDate);
    Booking getBooking(UUID id);


    List<Booking> getBookingsByUser(UUID userId);
    List<Booking> getAllBookings(); // Para admin

    List<DamageReport> getAllDamageReports(); //reportes de daños

    void confirmBookingPayment(UUID bookingId, UUID paymentId); // Llamado tras el pago
    void cancelBooking(UUID bookingId);
    void completeBooking(UUID bookingId);
    void registerReturn(UUID bookingId, boolean withDamage, String damageDescription, BigDecimal repairCost);
    void reportArrivalDamage(UUID bookingId, String description);
}
