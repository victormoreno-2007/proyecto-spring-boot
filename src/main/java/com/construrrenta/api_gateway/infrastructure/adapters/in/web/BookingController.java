package com.construrrenta.api_gateway.infrastructure.adapters.in.web;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.construrrenta.api_gateway.domain.model.booking.Booking;
import com.construrrenta.api_gateway.domain.ports.out.ManageBookingUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/bookings")
@RequiredArgsConstructor

public class BookingController {

    private final ManageBookingUseCase manageBookingUseCase;

    // 1. CREAR RESERVA (CORREGIDO)
    @PostMapping
    public ResponseEntity<Booking> createBooking(
        @AuthenticationPrincipal UUID userId, // <--- CAMBIO 1: El ID entra por aquí (del Token)
        @RequestBody BookingRequest request
    ) {
        // CAMBIO 2: Usamos 'userId' directo, ignoramos request.getUserId()
        Booking booking = manageBookingUseCase.createBooking(
            userId, 
            request.getToolId(),
            request.getStartDate(),
            request.getEndDate()
        );
        return ResponseEntity.ok(booking);
    }

    // 2. OBTENER UNA RESERVA POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(manageBookingUseCase.getBooking(id));
    }

    // 3. OBTENER RESERVAS DE UN USUARIO (Historial del Cliente)
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Booking>> getBookingsByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(manageBookingUseCase.getBookingsByUser(userId));
    }

    // 4. OBTENER TODAS LAS RESERVAS (Panel del Admin)
    @GetMapping
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(manageBookingUseCase.getAllBookings());
    }

    // 5. CANCELAR RESERVA
    @PostMapping("/{id}/cancel")
    public ResponseEntity<Void> cancelBooking(@PathVariable UUID id) {
        manageBookingUseCase.cancelBooking(id);
        return ResponseEntity.ok().build();
    }

    // 6. CONFIRMAR PAGO (Simulación - Lo llamaría Víctor tras pagar)
    @PostMapping("/{id}/confirm-payment")
    public ResponseEntity<Void> confirmPayment(@PathVariable UUID id, @RequestBody PaymentConfirmationRequest request) {
        manageBookingUseCase.confirmBookingPayment(id, request.getPaymentId());
        return ResponseEntity.ok().build();
    }

    // 7. FINALIZAR / DEVOLVER HERRAMIENTA (¡La joya de la corona!)
    @PostMapping("/{id}/return")
    public ResponseEntity<Void> returnTool(@PathVariable UUID id, @RequestBody ReturnRequest request) {
        manageBookingUseCase.registerReturn(
            id,
            request.isWithDamage(),
            request.getDamageDescription(),
            request.getRepairCost()
        );
        return ResponseEntity.ok().build();
    }
    // NUEVO: 8. HISTORIAL SEGURO (Tu tarea específica)
    // Extrae el ID del token automáticamente. No se le pasa por URL.
    @GetMapping("/my-history")
    public ResponseEntity<List<Booking>> getMyHistory(@AuthenticationPrincipal UUID userId) {
        // @AuthenticationPrincipal inyecta el ID del usuario logueado gracias a tu JwtValidationFilter
        return ResponseEntity.ok(manageBookingUseCase.getBookingsByUser(userId));
    }

    // ================= DTOs (Request Bodies) =================

    // DTO para crear reserva
    public static class BookingRequest {
        private UUID userId;
        private UUID toolId;
        private LocalDateTime startDate;
        private LocalDateTime endDate;

        public UUID getUserId() { return userId; }
        public void setUserId(UUID userId) { this.userId = userId; }
        public UUID getToolId() { return toolId; }
        public void setToolId(UUID toolId) { this.toolId = toolId; }
        public LocalDateTime getStartDate() { return startDate; }
        public void setStartDate(LocalDateTime startDate) { this.startDate = startDate; }
        public LocalDateTime getEndDate() { return endDate; }
        public void setEndDate(LocalDateTime endDate) { this.endDate = endDate; }
    }

    // DTO para confirmar pago
    public static class PaymentConfirmationRequest {
        private UUID paymentId;
        public UUID getPaymentId() { return paymentId; }
        public void setPaymentId(UUID paymentId) { this.paymentId = paymentId; }
    }

    // DTO para devolución
    public static class ReturnRequest {
        private boolean withDamage;
        private String damageDescription;
        private BigDecimal repairCost;

        public boolean isWithDamage() { return withDamage; }
        public void setWithDamage(boolean withDamage) { this.withDamage = withDamage; }
        public String getDamageDescription() { return damageDescription; }
        public void setDamageDescription(String damageDescription) { this.damageDescription = damageDescription; }
        public BigDecimal getRepairCost() { return repairCost; }
        public void setRepairCost(BigDecimal repairCost) { this.repairCost = repairCost; }
    }
}
