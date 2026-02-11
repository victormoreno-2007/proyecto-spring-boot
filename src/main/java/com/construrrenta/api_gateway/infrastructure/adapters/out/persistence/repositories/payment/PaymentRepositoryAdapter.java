package com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.payment;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.construrrenta.api_gateway.domain.model.payment.Payment;
import com.construrrenta.api_gateway.domain.ports.out.PaymentRepositoryPort;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.PaymentEntity;
import com.construrrenta.api_gateway.infrastructure.adapters.out.mappers.PaymentMapper;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PaymentRepositoryAdapter implements PaymentRepositoryPort{

    private final JpaPaymentRepository jpaPaymentRepository;
    private final PaymentMapper paymentMapper;
    private final com.construrrenta.api_gateway.infrastructure.adapters.out.persistence.repositories.booking.JpaBookingRepository jpaBookingRepository;

    @Override
    public Payment save(Payment payment) {
        PaymentEntity entity = paymentMapper.toEntity(payment);
        PaymentEntity saved = jpaPaymentRepository.save(entity);
        return paymentMapper.toDomain(saved);
    }

    @Override
    public Optional<Payment> findById(UUID id) {
        return jpaPaymentRepository.findById(id).map(paymentMapper::toDomain);
    }

    @Override
    public Optional<Payment> findByBookingId(UUID bookingId) {
        return jpaPaymentRepository.findByBookingId(bookingId).map(paymentMapper::toDomain);
    }

    @Override
    public List<Payment> findAll() {
        return jpaPaymentRepository.findAll().stream()
                .map(paymentMapper::toDomain)
                .toList();
    }

    @Override
    public List<Payment> findByUserId(UUID userId) {
        List<UUID> bookingIds = jpaBookingRepository.findByUserId(userId).stream()                
        .map(com.construrrenta.api_gateway.infrastructure.adapters.out.entities.BookingEntity::getId)
                .collect(Collectors.toList());

        return jpaPaymentRepository.findAll().stream()
                .filter(payment -> bookingIds.contains(payment.getBookingId()))
                .map(paymentMapper::toDomain)
                .collect(Collectors.toList());
    }
}
