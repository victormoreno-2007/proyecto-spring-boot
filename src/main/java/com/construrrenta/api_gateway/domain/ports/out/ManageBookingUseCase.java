package com.construrrenta.api_gateway.domain.ports.out;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.model.damage.DamageReport;

public interface ManageBookingUseCase {
    Booking createBooking(UUID userId, UUID toolId, LocalDateTime startDate, LocalDateTime endDate);
    Booking getBooking(UUID id);
    List<Booking> getBookingsByUser(UUID userId);
    List<Booking> getAllBookings();
    void cancelBooking(UUID bookingId);
    void completeBooking(UUID bookingId);
    void confirmBookingPayment(UUID bookingId, String externalPaymentReference);
    void reportArrivalDamage(UUID bookingId, String descripcion);
    void registerReturn(UUID bookingId, boolean withDamage, String damageDescription, BigDecimal repairCost);
    List<DamageReport> getAllDamageReports();
    List<Booking> getBookingsByProvider(UUID providerId);
    void approveBooking(UUID bookingId); 
    void rejectBooking(UUID bookingId);
}