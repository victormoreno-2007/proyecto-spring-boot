package com.construrrenta.api_gateway.domain.model.booking;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import com.construrrenta.api_gateway.domain.exceptions.DomainException;
import com.construrrenta.api_gateway.domain.model.tool.Tool;

public class Booking {
    private UUID id;
    private UUID userId;    
    private Tool tool;    
    private UUID paymentId; // Referencia al pago (Víctor) - Opcional al inicio
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private BigDecimal totalPrice;
    private BookingStatus status;

    private Booking() {}

    public Booking(UUID id, UUID userId, UUID toolId, UUID paymentId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal totalPrice, BookingStatus status) {
        this.id = id;
        this.userId = userId;
        this.tool = tool;
        this.paymentId = paymentId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalPrice = totalPrice;
        this.status = status;
    }
    
    public static Booking create(UUID userId, Tool tool, LocalDateTime startDate, LocalDateTime endDate) {
        if (startDate.isAfter(endDate)) {
            throw new DomainException("La fecha de inicio no puede ser posterior al fin");
        }
        if (startDate.isBefore(LocalDateTime.now())) {
            throw new DomainException("No se puede reservar en el pasado");
        }
        Booking booking = new Booking();
        booking.id = UUID.randomUUID();
        booking.userId = userId;
        booking.tool = tool;
        booking.startDate = startDate;
        booking.endDate = endDate;
        booking.status = BookingStatus.PENDING; // Nace pendiente de pago
        booking.paymentId = null;

        long days = ChronoUnit.DAYS.between(startDate, endDate);
        if (days < 1) days = 1; 
        
        booking.totalPrice = tool.getPricePerDay().multiply(BigDecimal.valueOf(days));

        return booking;
    }
    public static Booking reconstruct(UUID id, UUID userId, Tool tool, UUID paymentId, LocalDateTime startDate, LocalDateTime endDate, BigDecimal totalPrice, BookingStatus status) {
        Booking booking = new Booking();
        booking.id = id;
        booking.userId = userId;
        booking.tool = tool;
        booking.paymentId = paymentId;
        booking.startDate = startDate;
        booking.endDate = endDate;
        booking.totalPrice = totalPrice;
        booking.status = status;
        return booking;
    }

    // Método para asociar el pago cuando Víctor lo haga
    public void confirmPayment(UUID paymentId) {
        this.paymentId = paymentId;
        this.status = BookingStatus.CONFIRMED;
    }

    // ... métodos existentes ...
    public void complete() {
        // Regla de negocio: Solo se puede completar si ya estaba confirmada
        if (this.status != BookingStatus.CONFIRMED) {
            throw new DomainException("Solo se pueden finalizar reservas que están confirmadas.");
        }
        this.status = BookingStatus.COMPLETED;
    }
    
    public void cancel() {
        if (this.status == BookingStatus.COMPLETED) {
             throw new DomainException("No se puede cancelar una reserva ya finalizada.");
        }
        this.status = BookingStatus.CANCELLED;
    }
    public void approve() {
        this.status = BookingStatus.CONFIRMED; 
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public Tool getTool() { return tool; }
    public UUID getToolId() { return tool != null ? tool.getId() : null; }
    public UUID getPaymentId() { return paymentId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public BigDecimal getTotalPrice() { return totalPrice; }
    public BookingStatus getStatus() { return status; }



}
