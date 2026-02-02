package com.construrrenta.api_gateway.domain.ports.out;

import java.util.Optional;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.model.payment.Payment;

public interface PaymentRepositoryPort {
    
    Payment save(Payment payment);
    Optional<Payment> findById(UUID id);
    Optional<Payment> findByMyBookingId(UUID bookingId);
}
