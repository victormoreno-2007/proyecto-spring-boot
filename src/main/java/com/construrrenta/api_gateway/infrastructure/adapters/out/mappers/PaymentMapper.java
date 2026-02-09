package com.construrrenta.api_gateway.infrastructure.adapters.out.mappers;

import org.mapstruct.Mapper;

import com.construrrenta.api_gateway.domain.model.payment.Payment;
import com.construrrenta.api_gateway.infrastructure.adapters.out.entities.PaymentEntity;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentEntity toEntity(Payment payment);

    default Payment toDomain(PaymentEntity entity) {
        if (entity == null) {
            return null;
        }
        return Payment.reconstruct(
            entity.getId(),
            entity.getAmount(),
            entity.getPaymentDate(),
            entity.getMethod(),
            entity.getStatus(),
            entity.getBookingId()
        );
    }

}
