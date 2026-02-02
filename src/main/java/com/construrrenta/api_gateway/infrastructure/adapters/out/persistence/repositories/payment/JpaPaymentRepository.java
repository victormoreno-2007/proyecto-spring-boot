package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.payment;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.PaymentEntity;
import java.util.Optional;


public interface JpaPaymentRepository extends JpaRepository<PaymentEntity, UUID> {
    Optional<PaymentEntity> findByBookingId(UUID bookingId);

    
}
