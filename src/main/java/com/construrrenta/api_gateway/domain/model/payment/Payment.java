package com.construrrenta.api_gateway.domain.model.payment;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;

public class Payment {
    private UUID id;
    private BigDecimal amount;
    private LocalDateTime paymentDate;
    private PaymentMethod method;
    private PaymentStatus status;
    private UUID bookingId;
    public Payment() {
    }

    public static Payment create(BigDecimal amount, PaymentMethod method, UUID bookingId) {
        if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new DomainException("El monto del pago debe ser mayor a 0");
        }
        if (bookingId == null) {
            throw new DomainException("El pago debe estar asociado a una reserva");
        }

        Payment payment = new Payment();
        payment.id = UUID.randomUUID();
        payment.amount = amount;
        payment.paymentDate = LocalDateTime.now();
        payment.method = method;
        payment.status = PaymentStatus.PENDING;
        payment.bookingId = bookingId;
        return payment;
    }

    // NUEVO MÉTODO: Para reconstruir el objeto desde la Base de Datos
    public static Payment reconstruct(UUID id, BigDecimal amount, LocalDateTime paymentDate, PaymentMethod method, PaymentStatus status, UUID bookingId) {
        Payment payment = new Payment();
        payment.id = id;
        payment.amount = amount;
        payment.paymentDate = paymentDate;
        payment.method = method;
        payment.status = status;
        payment.bookingId = bookingId;
        return payment;
    }
    
    public UUID getId() { return id; }
    public BigDecimal getAmount() { return amount; }
    public LocalDateTime getPaymentDate() { return paymentDate; }
    public PaymentMethod getMethod() { return method; }
    public PaymentStatus getStatus() { return status; }
    public UUID getBookingId() { return bookingId; }

    public void complete() {
        this.status = PaymentStatus.COMPLETED;
    }
    
    public void fail() {
        this.status = PaymentStatus.FAILED;
    }

}
